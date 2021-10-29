/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.controller;

import com.funesoft.dto.AdherenteDTO;
import com.funesoft.dto.SocioBajaDTO;
import com.funesoft.dto.SocioDTO;
import com.funesoft.model.*;
import com.funesoft.repository.EstadoRepository;
import com.funesoft.repository.MotivoBajaRepository;
import com.funesoft.repository.SocioBajaRepository;
import com.funesoft.repository.SocioRepository;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.funesoft.utilities.BusinessException;
import com.funesoft.utilities.CurrentUser;
import com.funesoft.utilities.EstadoEnum;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.ResourceUtils;

/**
 *
 * @author faust
 */
@Controller
public class SocioController {
    
    @Autowired
    private AdherenteController adherenteController;
    
    @Autowired
    private SocioRepository socioRepository;

    @Autowired
    private CoberturaController coberturaController;

    @Autowired
    private MotivoBajaRepository motivoBajaRepository;

    @Autowired
    private SocioBajaRepository socioBajaRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public List<Socio> getSocios (Socio socio){
        return socioRepository.findAll(Example.of(socio));
    }
    
    public List<String> getDNIs (){
        return socioRepository.findDniByOrderByDniDesc();
    }

    public Socio insertSocio (@NotNull SocioDTO socioDTO){

        Socio socio = new Socio(socioDTO);
        
        //CALCULO LA COBERTURA
        socio.setFechaCobertura(coberturaController.calculoCobertura(socio));

        socio.setUsuarioModifica(CurrentUser.getInstance());
        socio.setEstado(estadoRepository.findByNroEstado(EstadoEnum.ALTA.getCodigo()));

        socio = socioRepository.save(socio);
        
        final Integer idSocio = socio.getId();
        final List<AdherenteDTO> adherentesDTO = socioDTO.getAdherentesAltaDTO();
        if (CollectionUtils.isNotEmpty(adherentesDTO)) {
            adherentesDTO.forEach(adherenteDTO -> {
                    adherenteDTO.setIdSocio(idSocio);
                    adherenteController.insertAdherente(adherenteDTO);
                }
            );
        }
        
        return socio;
    }

    public Socio updateSocio (@NotNull Socio socio) throws BusinessException {
        if(!(socioRepository.findById(socio.getId()).isPresent())){
            throw new BusinessException("El socio informado no existe");
        }
        socio.setUsuarioModifica(CurrentUser.getInstance());
        return socioRepository.save(socio);
    }

    public Socio deleteSocio (@NotNull SocioBajaDTO socioBajaDTO) throws BusinessException {

        //SE BUSCA EL SOCIO POR EL ID
        Optional<Socio> socio = socioRepository.findById(socioBajaDTO.getIdSocio());
        Optional<MotivoBaja> motivoBaja = motivoBajaRepository.findById(socioBajaDTO.getIdMotivoBaja());

        if(!socio.isPresent()) {
            throw new BusinessException("El socio informado no existe");
        }

        if(!motivoBaja.isPresent()) {
            throw new BusinessException("El motivo informado no existe");
        }

        //ACTUALIZO EL ESTADO DEL SOCIO
        socio.get().setEstado(estadoRepository.findByNroEstado(EstadoEnum.BAJA.getCodigo()));
        socio.get().setUsuarioModifica(CurrentUser.getInstance());

        //CARGO EL MOTIVO DE LA BAJA
        socioBajaRepository.save(
                new SocioBaja(
                        socio.get(),
                        motivoBaja.get(),
                        CurrentUser.getInstance()
                )
        );

        return socioRepository.save(socio.get());
    }

    public String generateReport(HttpServletResponse response) throws SQLException, IOException, JRException {
        try {

            response.setContentType("application/x-download");
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"socios.pdf\""));

            OutputStream out = response.getOutputStream();

            String reportPath = "classpath:padron_socios.jrxml";

            File file = ResourceUtils.getFile(reportPath);
            InputStream input = new FileInputStream(file);
            JasperReport jasperReport = JasperCompileManager.compileReport(input);

            Connection conn = jdbcTemplate.getDataSource().getConnection();

            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);
            JasperExportManager.exportReportToPdfStream(jasperPrint, out);

            return "XLS File Generated";
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            throw e;
        }

    }

    public String generateReportXLS(HttpServletResponse response) throws SQLException, IOException, JRException {
        try {

            String reportPath = "classpath:padron_socios.jrxml";

            File file = ResourceUtils.getFile(reportPath);
            InputStream input = new FileInputStream(file);
            JasperReport jasperReport = JasperCompileManager.compileReport(input);
            Connection conn = jdbcTemplate.getDataSource().getConnection();
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);

            JRXlsxExporter xlsExporter = new JRXlsxExporter();

            String outXlsName = "padron_socios.xlsx";
            xlsExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outXlsName));
            SimpleXlsxReportConfiguration xlsReportConfiguration = new SimpleXlsxReportConfiguration();
            xlsReportConfiguration.setOnePagePerSheet(false);
            xlsReportConfiguration.setRemoveEmptySpaceBetweenRows(true);
            xlsReportConfiguration.setDetectCellType(false);
            xlsReportConfiguration.setWhitePageBackground(false);
            xlsExporter.setConfiguration(xlsReportConfiguration);

            xlsExporter.exportReport();

            return "XLS File Generated";

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            throw e;
        }

    }

}
