package com.funesoft.controller;

import com.funesoft.model.Comprobante;
import com.funesoft.model.ParametroEmpresa;
import com.funesoft.model.Socio;
import com.funesoft.repository.ComprobanteRepository;
import com.funesoft.repository.ParametroEmpresaRepository;
import com.funesoft.repository.SocioRepository;
import com.funesoft.utilities.BusinessException;
import com.funesoft.utilities.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Controller
public class ComprobanteController {

    @Autowired
    private ComprobanteRepository comprobanteRepository;

    @Autowired
    private SocioRepository socioRepository;

    @Autowired
    private TarifaController tarifaController;

    @Autowired
    private ParametroEmpresaRepository parametroEmpresaRepository;

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

        //Recupero el último comprobante
        Optional<Comprobante> ultimoCbte = comprobanteRepository.findUltimoComprobante();
        BigInteger nroComprobante = ultimoCbte.isPresent() ? ultimoCbte.get().getNroComprobante().add(BigInteger.ONE) : BigInteger.ONE;

        List<Comprobante> result = new ArrayList<>();

        for(Socio socio : socios){

            //RECUPERO EL IMPORTE QUE PAGA EL SOCIO
            Map<Integer, Float> map = tarifaController.getValorTarifaByAsociado(socio);

            Comprobante cbte = newComprobante(
                    nroComprobante,
                    map.get(0).doubleValue() - socio.getSaldo(),
                    socio
            );

            //DECREMENTO EL SALDO DEL SOCIO
            socio.setSaldo(socio.getSaldo() - map.get(0).doubleValue());
            socioRepository.save(socio);

            result.add(cbte);

            //INCREMENTO EL PRÓXIMO NRO DE COMPROBANTE
            nroComprobante.add(BigInteger.ONE);

        }

        return result;

    }

    private Comprobante newComprobante(BigInteger nroComprobante, Double importeTotal, Socio socio){

        return comprobanteRepository.save(
                new Comprobante(
                        nroComprobante,
                        importeTotal,
                        CurrentUser.getInstance(),
                        socio
                )
        );

    }


}
