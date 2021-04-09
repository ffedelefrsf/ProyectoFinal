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
        dto.setSubtitulo("De " + (Integer.parseInt(item.get(0)[1].toString()) + Integer.parseInt(item.get(1)[1].toString())));

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

}
