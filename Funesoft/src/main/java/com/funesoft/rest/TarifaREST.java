/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.rest;

import com.funesoft.controller.TarifaController;
import com.funesoft.dto.FunesoftResponseDTO;
import com.funesoft.dto.TarifaDTO;
import com.funesoft.dto.ResponseDTO;
import com.funesoft.model.Tarifa;
import com.funesoft.utilities.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
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
@RequestMapping("tarifa")
@Api(description = "Servicios de ABMC para tarifas.")
public class TarifaREST {
    
    @Autowired
    private TarifaController tarifaController;
    
    @PostMapping(path = "getAll")
    @ApiOperation(value = "Obtiene un listado de todas las taridas registradas en el sistema con los parámetros establecidos.")
    public FunesoftResponseDTO getAll(@RequestBody TarifaDTO tarifaDTO){
        try{
            return new FunesoftResponseDTO(true, tarifaController.getAllTarifas(tarifaDTO), null, null);
        }catch (Exception exception){
            return new FunesoftResponseDTO(false, null, exception.getMessage(), exception);
        }
    }
    
    @PostMapping(path = "insert")
    @ApiOperation(value = "Inserta una tarifa en el sistema.")
    public FunesoftResponseDTO insert(@Valid @RequestBody TarifaDTO tarifaDTO){
        try{
            return new FunesoftResponseDTO(true, tarifaController.insertTarifa(tarifaDTO), null, null);
        }catch (BusinessException businessException){
            return new FunesoftResponseDTO(false, null, businessException.getMessage(), businessException);
        }
    }
    
    @PostMapping(path = "update")
    @ApiOperation(value = "Actualiza una tarifa en el sistema.")
    public FunesoftResponseDTO update(@Valid @RequestBody Tarifa tarifa){
        try{
            return new FunesoftResponseDTO(true, tarifaController.updateTarifa(tarifa), null, null);
        }catch (BusinessException businessException){
            return new FunesoftResponseDTO(false, null, businessException.getMessage(), businessException);
        }
    }
    
    @PostMapping(path = "delete")
    @ApiOperation(value = "Elimina una tarifa en el sistema.")
    public FunesoftResponseDTO delete(@RequestBody TarifaDTO tarifaDTO){
        try{
            return new FunesoftResponseDTO(true, tarifaController.deleteTarifa(tarifaDTO.getId()), null, null);
        }catch (BusinessException exception){
            return new FunesoftResponseDTO(false, null, exception.getMessage(), exception);
        }
    }
    
}
