/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.rest;

import com.funesoft.controller.CoberturaController;
import com.funesoft.dto.FechaCoberturaGetRequestDTO;
import com.funesoft.dto.FunesoftResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author faust
 */
@RestController
@RequestMapping("fechaCobertura")
public class FechaCoberturaREST {
    
    @Autowired
    private CoberturaController coberturaController;
    
    @PostMapping(path = "get", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public FunesoftResponseDTO getFechaCobertura(@RequestBody FechaCoberturaGetRequestDTO fechaCoberturaGetRequestDTO) {
        try{
            return new FunesoftResponseDTO(true, coberturaController.calcularByFechaAndIdEnfermedad(fechaCoberturaGetRequestDTO.getFechaNacimientoAsociado(), fechaCoberturaGetRequestDTO.getIdEnfermedadAsociado()), null, null);
        }catch (Exception exception){
            return new FunesoftResponseDTO(false, null, exception.getMessage(), exception);
        }
    }
    
}
