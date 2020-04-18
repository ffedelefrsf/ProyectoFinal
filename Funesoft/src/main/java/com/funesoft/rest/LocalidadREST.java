/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.rest;

import com.funesoft.controller.LocalidadController;
import com.funesoft.dto.FunesoftResponseDTO;
import com.funesoft.dto.LocalidadDTO;
import com.funesoft.dto.ResponseDTO;
import com.funesoft.utilities.BusinessException;
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
@RequestMapping("localidad")
@Api(description = "Servicio para consultar las localidades.")
public class LocalidadREST {
    
    @Autowired
    private LocalidadController localidadController;
    
    @PostMapping(path = "getAll")
    public FunesoftResponseDTO getAll(@RequestBody LocalidadDTO localidadRequestDTO){
        try {
            return new FunesoftResponseDTO(true, localidadController.getAllLocalidades(localidadRequestDTO), null, null);
        } catch (Exception exception) {
            return new FunesoftResponseDTO(false, null, exception.getMessage(), exception);
        }
    }
}
