/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.rest;

import com.funesoft.controller.MotivoBajaController;
import com.funesoft.dto.FunesoftResponseDTO;
import com.funesoft.model.Socio;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author faust
 */
@RestController
@RequestMapping("motivoBaja")
public class MotivoBajaREST {
    
    @Autowired
    private MotivoBajaController motivoBajaController;
    
    @GetMapping("getAll")
    @ApiOperation(value = "Obtiene un listado de todos los motivos de baja de socio registrados en el sistema.")
    public FunesoftResponseDTO getMotivosBaja(){
        try{
            return new FunesoftResponseDTO(true, motivoBajaController.getAll(), null, null);
        }catch (Exception exception){
            return new FunesoftResponseDTO(false, null, exception.getMessage(), exception);
        }
    }

    @PostMapping("getMotivo")
    @ApiOperation(value = "Obtiene el motivo de baja de un socio dado")
    public FunesoftResponseDTO getMotivoBaja(@RequestBody Socio socio){
        try{
            return new FunesoftResponseDTO(true, motivoBajaController.getMotivo(socio), null, null);
        }catch (Exception exception){
            return new FunesoftResponseDTO(false, null, exception.getMessage(), exception);
        }
    }
    
}
