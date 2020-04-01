/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.rest;

import com.funesoft.controller.SocioController;
import com.funesoft.dto.FunesoftResponseDTO;
import com.funesoft.dto.ResponseDTO;
import com.funesoft.dto.SocioDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

/**
 *
 * @author faust
 */
@RestController
@RequestMapping("socio")
@Api(description = "Servicios para AMBC de socios.")
public class SocioREST {
    
    @Autowired
    private SocioController socioController;
    
    @GetMapping("getAll")
    @ApiOperation(value = "Obtiene un listado de todos los socios registrados en el sistema.")
    public ResponseDTO getAll(){
        return new FunesoftResponseDTO(
                true,
                socioController.getAllSocios(),
                null,
                null
        );
    }
    
    @PostMapping("insert")
    @ApiOperation(value = "Inserta un nuevo socio", response = SocioDTO.class)
    private ResponseDTO insertSocio(@Valid @RequestBody SocioDTO socioDTO){
        return new FunesoftResponseDTO(
                true,
                socioController.insertSocio(socioDTO),
                null,
                null
        );
    }
}
