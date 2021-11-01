package com.funesoft.controller;

import com.funesoft.dto.ComprobanteDTO;
import com.funesoft.model.*;
import com.funesoft.repository.*;
import com.funesoft.utilities.BusinessException;
import com.funesoft.utilities.CurrentUser;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Controller
public class ComprobanteController {

    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ComprobanteRepository comprobanteRepository;

    @Autowired
    private SocioRepository socioRepository;

    @Autowired
    private TarifaController tarifaController;

    @Autowired
    private ParametroEmpresaRepository parametroEmpresaRepository;

    @Autowired
    private ImpresionComprobanteRepository impresionComprobanteRepository;

    @Autowired
    private AdherenteRepository adherenteRepository;

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private PagoController pagoController;

    @Autowired
    private EntityManager entityManager;

    public List<Comprobante> generarComprobantesMasivos() throws BusinessException, ExecutionException, InterruptedException {

        Future<List<Comprobante>> listComprobantes = generarComprobantesMasivosAsync();

        return listComprobantes.get();

    }

    @Async
    public Future<List<Comprobante>> generarComprobantesMasivosAsync() throws BusinessException {

        //RECUPERO LA LISTA DE SOCIOS ACTIVOS
        List<Socio> socios = socioRepository.findAllActivo();

        if (socios.isEmpty()) {
            throw new BusinessException("No existen socios dados de alta");
        }

        return new AsyncResult(createComprobantes(socios));

    }

    public Comprobante generarComprobante(final Integer idSocio) throws BusinessException {

        //BUSCO LA EL VALOR QUE PAGARÍA EL SOCIO Y SUS ADHERENTES A TRAVÉS DE LA TARIFA

        Optional<Socio> socio = socioRepository.findByIdActivo(idSocio);

        if (!socio.isPresent()) {
            throw new BusinessException("El socio informado no existe");
        }

        List<Socio> input = new ArrayList<>();
        input.add(socio.get());

        return createComprobantes(input).get(0);

    }

    private List<Comprobante> createComprobantes(List<Socio> socios) throws BusinessException {

        List<Comprobante> result = new ArrayList<>();

        //Recupero el último comprobante
        Optional<Comprobante> ultimoCbte = comprobanteRepository.findUltimoComprobante();
        BigInteger nroComprobante = ultimoCbte.isPresent() ? ultimoCbte.get().getNroComprobante().add(BigInteger.ONE) : BigInteger.ONE;

        for (Socio socio : socios) {

            //RECUPERO EL IMPORTE QUE PAGA EL SOCIO
            Map<Integer, Float> map = tarifaController.getValorTarifaByAsociado(socio);

            //NO TENGO EN CUENTA EL SALDO DEL SOCIO. SI TIENE SALDO NEGATIVO SE LE INFORMADA MEDIANTE UN STRING
            Double valorCbte = map.get(0).doubleValue(); // - socio.getSaldo();

            Comprobante cbte = newComprobante(
                    nroComprobante,
                    valorCbte < 0D ? 0D : valorCbte,
                    socio
            );

            //DECREMENTO EL SALDO DEL SOCIO
            socio.setSaldo(socio.getSaldo() - map.get(0).doubleValue());
            socioRepository.save(socio);

            result.add(cbte);

            cargarDatosImpresion(map, cbte);

            //INCREMENTO EL PRÓXIMO NRO DE COMPROBANTE
            nroComprobante = nroComprobante.add(BigInteger.ONE);

        }

        return result;

    }

    private Comprobante newComprobante(BigInteger nroComprobante, Double importeTotal, Socio socio) {

        return comprobanteRepository.save(
                new Comprobante(
                        nroComprobante,
                        importeTotal,
                        CurrentUser.getInstance(),
                        socio,
                        false
                )
        );

    }

    private void cargarDatosImpresion(Map<Integer, Float> map, Comprobante cbte) {

        //ELIMINO EL TOTAL DEL CBTE
        map.remove(0);
        
        for (Map.Entry<Integer, Float> entry : map.entrySet()) {

            //CHEQUEO Y RECUPERO SI EL MAP TIENE UN ADHERENTE
            Optional<Adherente> adh = adherenteRepository.findByDni(entry.getKey());

            ImpresionComprobante impresion = new ImpresionComprobante();
            impresion.setComprobante(cbte);

            if(adh.isPresent()){
                impresion.setAdherente(adh.get());
                impresion.setImporte(entry.getValue().doubleValue());
                impresion.setUsuarioModifica(CurrentUser.getInstance());
                impresionComprobanteRepository.save(impresion);
            }

        }

    }

    public String generateReport(HttpServletResponse response) throws SQLException, IOException, JRException {
        try {

            response.setContentType("application/x-download");
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"comprobantes.pdf\""));

            OutputStream out = response.getOutputStream();

            String reportPath = "classpath:comprobantes.jrxml";

            File file = ResourceUtils.getFile(reportPath);
            InputStream input = new FileInputStream(file);
            JasperReport jasperReport = JasperCompileManager.compileReport(input);

            Connection conn = jdbcTemplate.getDataSource().getConnection();

            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);
            JasperExportManager.exportReportToPdfStream(jasperPrint, out);

            actualizarImpresos();

            return "PDF File Generated";
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            throw e;
        }

    }

    private List<Comprobante> actualizarImpresos() {
        List<Comprobante> listImpresos = comprobanteRepository.findByImpreso(false);

        for (Comprobante cbte : listImpresos) {
            cbte.setImpreso(true);
        }

        return comprobanteRepository.saveAll(listImpresos);

    }

    public List<ComprobanteDTO> getAll(Comprobante comprobante) throws ParseException {
        List<Comprobante> cbtes = comprobanteRepository.findAll(Example.of(comprobante));
        List<ComprobanteDTO> cbtesDTO = new ArrayList<>();

        for (Comprobante item: cbtes) {
            ComprobanteDTO dto = new ComprobanteDTO();
            dto.setId(item.getId());
            dto.setImporteTotal(item.getImporteTotal());
            dto.setImpreso(item.getImpreso());
            dto.setNroComprobante(item.getNroComprobante());
            dto.setPagado(pagoController.isPagado(item));
            dto.setSocio(item.getSocio());
            dto.setVencimiento(getVencimiento(item));
            cbtesDTO.add(dto);
        }

        return cbtesDTO;
    }

    public String getVencimiento(Comprobante comprobante) throws ParseException {

        List<Timestamp> fecha_gen = entityManager.createNativeQuery("SELECT CA.FECHA " +
                "FROM comprobantes_audit CA " +
                "WHERE CA.METODO = 'INSERT' AND CA.ID = " + comprobante.getId()).getResultList();

        Date date = new Date(fecha_gen.get(0).getTime());

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 3);
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");

        return format1.format(cal.getTime());
    }

}
