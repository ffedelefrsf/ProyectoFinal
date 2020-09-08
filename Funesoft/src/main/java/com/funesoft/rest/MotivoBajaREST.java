/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.rest;

import com.funesoft.controller.MotivoBajaController;
import com.funesoft.dto.FunesoftResponseDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    
}
