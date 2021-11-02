/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.funesoft.dto.*;
import com.funesoft.model.*;
import com.funesoft.repository.*;
import com.funesoft.utilities.BusinessException;
import com.funesoft.utilities.CurrentUser;
import com.funesoft.utilities.EstadoEnum;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class EstadisticaController {
    
    @Autowired
    private EstadisticaRepository estadisticaRepository;

    Calendar calendar = Calendar.getInstance();

    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public List<EstadisticaDTO> getEstadisticasHeader(){
        List<EstadisticaDTO> estadisticasDTO = new ArrayList<>();
        estadisticasDTO.add(getIngresoAnual());
        estadisticasDTO.add(getAIngresar());
        estadisticasDTO.add(getSociosActivos());
        estadisticasDTO.add(getNuevosSocios());
        return estadisticasDTO;
    }

    public EstadisticaDTO getIngresoAnual(){

        EstadisticaDTO dto = new EstadisticaDTO();
        Double ingresoAnual = 0D;
        String anioInicial = "", sign = "";
        Double valorInicial = 0D, valorFinal = 0D;

        List<Object[]> items = estadisticaRepository.findIngresoAnual();
        List<Object[]> itemsVentas = estadisticaRepository.findVentasAnual();

        for (Object[] item : items) {
            if(Integer.parseInt(item[0].toString()) == calendar.get(Calendar.YEAR)){
                ingresoAnual = Double.valueOf(item[1].toString());
                valorFinal = Double.parseDouble(item[1].toString());
            } else {
                valorInicial = Double.parseDouble(item[1].toString());
                anioInicial = item[0].toString();
            }
        }

        for (Object[] item : itemsVentas) {
            if(Integer.parseInt(item[0].toString()) == calendar.get(Calendar.YEAR)){
                ingresoAnual += Double.valueOf(item[1].toString());
                valorFinal += Double.parseDouble(item[1].toString());
            } else {
                valorInicial += Double.parseDouble(item[1].toString());
                anioInicial += item[0].toString();
            }
        }

        if(items.size() > 1 || itemsVentas.size() > 1){
            dto.setTitulo("Ingreso anual");
            dto.setValor("$" + ingresoAnual);
            dto.setSubtitulo((valorInicial > valorFinal ? "" : "+") + new DecimalFormat("#").format((((valorFinal - valorInicial)/valorInicial)*100)) + "% que en " + anioInicial);
        } else if(items.size() == 1 && itemsVentas.size() == 1){
            dto.setTitulo("Ingreso anual");
            dto.setValor("$" + ingresoAnual);
            dto.setSubtitulo("En " + calendar.get(Calendar.YEAR));
        } else {
            dto.setTitulo("Ingreso anual");
            dto.setValor("$0.0");
            dto.setSubtitulo("En " + calendar.get(Calendar.YEAR));
        }

        return dto;
    }

    public EstadisticaDTO getAIngresar(){

        EstadisticaDTO dto = new EstadisticaDTO();

        List<Object[]> item = estadisticaRepository.findAIngresar();
        Calendar cal = Calendar.getInstance();

        dto.setTitulo("A ingresar");
        dto.setValor("$" + item.get(0)[1].toString());

        dto.setSubtitulo("De " + new SimpleDateFormat("MMMM").format(cal.getTime()));

        return dto;
    }

    public EstadisticaDTO getSociosActivos(){

        EstadisticaDTO dto = new EstadisticaDTO();

        List<Object[]> item = estadisticaRepository.findSociosActivos();

        dto.setTitulo("Socios activos");
        dto.setValor(item.get(0)[1].toString());
        if(item.size() > 1){
            dto.setSubtitulo("De " + (Integer.parseInt(item.get(0)[1].toString()) + Integer.parseInt(item.get(1)[1].toString())));
        } else {
            dto.setSubtitulo("De " + (Integer.parseInt(item.get(0)[1].toString())));
        }


        return dto;
    }

    public EstadisticaDTO getNuevosSocios(){

        EstadisticaDTO dto = new EstadisticaDTO();

        List<Object[]> item = estadisticaRepository.findNuevosSocios();

        dto.setTitulo("Nuevos socios");
        dto.setValor(item.get(0)[0].toString());
        dto.setSubtitulo("En " + item.get(0)[1].toString());

        return dto;
    }

    public List<DeudoresDTO> getDeudores(){

        List<DeudoresDTO> deudores = new ArrayList<>();

        estadisticaRepository.findDeudores().forEach(
                (deudor) -> {
                    DeudoresDTO dto = new DeudoresDTO();
                    dto.setIdSocio(Integer.valueOf(deudor[0].toString()));
                    dto.setNombre(deudor[1].toString());
                    dto.setApellido(deudor[2].toString());
                    dto.setSaldo(Double.valueOf(deudor[3].toString()));
                    dto.setMesesDebe(Integer.valueOf(deudor[4].toString()));
                    dto.setFechaPrimerCbte(deudor[5].toString().split(" ")[0]);
                    deudores.add(dto);
                }
        );

        return deudores;
    }

    public List<HistoricoDTO> getHistorico(){

        List<HistoricoDTO> historicos = new ArrayList<>();

        List<Object[]> economico = estadisticaRepository.findMejorEconomico();

        HistoricoDTO histEconomico = new HistoricoDTO();
        histEconomico.setTexto("Mejor rendimiento económico en planes");
        histEconomico.setValor("+$" + economico.get(0)[1].toString());
        histEconomico.setAnio(economico.get(0)[0].toString());
        historicos.add(histEconomico);

        List<Object[]> alta =  estadisticaRepository.findMejorAlta();
        HistoricoDTO histAlta = new HistoricoDTO();
        histAlta.setTexto("Mejor rendimiento en altas");
        histAlta.setValor(alta.get(0)[1].toString());
        histAlta.setAnio(alta.get(0)[0].toString());
        historicos.add(histAlta);

        List<Object[]> venta = estadisticaRepository.findVentasHistoricas();
        HistoricoDTO histVenta = new HistoricoDTO();
        histVenta.setTexto("Mejor rendimiento en venta de servicios");
        histVenta.setValor("+$" + venta.get(0)[1].toString());
        histVenta.setAnio(venta.get(0)[0].toString());
        historicos.add(histVenta);

        return historicos;
    }

    public String reportEstadoSocios(HttpServletResponse response) throws SQLException, IOException, JRException {
        try {
            response.setContentType("application/x-download");
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"estados_de_socios.pdf\""));
            OutputStream out = response.getOutputStream();
            String reportPath = "classpath:socios_estados.jrxml";
            File file = ResourceUtils.getFile(reportPath);
            InputStream input = new FileInputStream(file);
            JasperReport jasperReport = JasperCompileManager.compileReport(input);
            Connection conn = jdbcTemplate.getDataSource().getConnection();
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);
            JasperExportManager.exportReportToPdfStream(jasperPrint, out);
            return "PDF File Generated";
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            throw e;
        }
    }
    public String reportComprobantesYPagos(HttpServletResponse response) throws SQLException, IOException, JRException {
        try {
            response.setContentType("application/x-download");
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"comprobantes_y_pagos.pdf\""));
            OutputStream out = response.getOutputStream();
            String reportPath = "classpath:comprobantes_pagos_socios.jrxml";
            File file = ResourceUtils.getFile(reportPath);
            InputStream input = new FileInputStream(file);
            JasperReport jasperReport = JasperCompileManager.compileReport(input);
            Connection conn = jdbcTemplate.getDataSource().getConnection();
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);
            JasperExportManager.exportReportToPdfStream(jasperPrint, out);
            return "PDF File Generated";
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            throw e;
        }
    }

}
