/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.rest;

import com.funesoft.controller.EnfermedadController;
import com.funesoft.dto.EnfermedadDTO;
import com.funesoft.dto.FunesoftResponseDTO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author faust
 */
@RestController
@RequestMapping("enfermedad")
@Api(description = "Servicio para consultar las enfermedades.")
public class EnfermedadREST {
    
    @Autowired
    private EnfermedadController enfermedadController;
    
    @PostMapping(path = "getAll")
    public FunesoftResponseDTO getAll(@RequestBody EnfermedadDTO enfermedadDTO){
        try {
            return new FunesoftResponseDTO(true, enfermedadController.getAllEnfermedades(enfermedadDTO), null, null);
        } catch (Exception exception) {
            return new FunesoftResponseDTO(false, null, exception.getMessage(), exception);
        }
    }
    
}
