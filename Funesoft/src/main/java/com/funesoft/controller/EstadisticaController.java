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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;

import javax.persistence.NoResultException;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class EstadisticaController {
    
    @Autowired
    private EstadisticaRepository estadisticaRepository;

    Calendar calendar = Calendar.getInstance();

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
        String ingresoAnual = "", anioInicial = "", sign = "";
        Double valorInicial = 0D, valorFinal = 0D;

        List<Object[]> items = estadisticaRepository.findIngresoAnual();

        for (Object[] item : estadisticaRepository.findIngresoAnual()) {
            if(Integer.parseInt(item[0].toString()) == calendar.get(Calendar.YEAR)){
                ingresoAnual = "$" + item[1].toString();
                valorFinal = Double.parseDouble(item[1].toString());
            } else {
                valorInicial = Double.parseDouble(item[1].toString());
                anioInicial = item[0].toString();
            }
        }

        sign = valorInicial > valorFinal ? "-" : "+";

        if(items.size() > 1){
            dto.setTitulo("Ingreso anual");
            dto.setValor(ingresoAnual);
            dto.setSubtitulo(sign  + new DecimalFormat("#").format((((valorFinal - valorInicial)/valorInicial)*100)) + "% que en " + anioInicial);
        } else if(items.size() == 1){
            dto.setTitulo("Ingreso anual");
            dto.setValor(ingresoAnual);
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
        histEconomico.setTexto("Mejor rendimiento económico");
        histEconomico.setValor("+$" + economico.get(0)[1].toString());
        histEconomico.setAnio(economico.get(0)[0].toString());
        historicos.add(histEconomico);

        List<Object[]> alta =  estadisticaRepository.findMejorAlta();
        HistoricoDTO histAlta = new HistoricoDTO();
        histAlta.setTexto("Mejor rendimiento en altas");
        histAlta.setValor(alta.get(0)[1].toString());
        histAlta.setAnio(alta.get(0)[0].toString());
        historicos.add(histAlta);

        return historicos;
    }

}
