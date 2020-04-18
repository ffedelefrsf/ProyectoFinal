/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.rest;

import com.funesoft.controller.ProvinciaController;
import com.funesoft.dto.FunesoftResponseDTO;
import com.funesoft.dto.ProvinciaDTO;
import com.funesoft.dto.ResponseDTO;
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
@RequestMapping("provincia")
@Api(description = "Servicio para consultar las provincias.")
public class ProvinciaREST {
    
    @Autowired
    private ProvinciaController provinciaController;
    
    @PostMapping(path = "getAll")
    public FunesoftResponseDTO getAll(@RequestBody ProvinciaDTO provinciaRequestDTO){
        try{
            return new FunesoftResponseDTO(true, provinciaController.getAllProvincias(provinciaRequestDTO), null, null);
        }catch (Exception exception){
            return new FunesoftResponseDTO(false, null, exception.getMessage(), exception);
        }
    }
}
