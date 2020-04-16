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
    public ResponseDTO getAll(@RequestBody TarifaDTO getTarifasRequestDTO){
        try{
            return new FunesoftResponseDTO(true, tarifaController.getAllTarifas(getTarifasRequestDTO), null, null);
        }catch (Exception exception){
            return new FunesoftResponseDTO(false, null, exception.getMessage(), exception);
        }
    }
    
    @PostMapping(path = "insert")
    public ResponseDTO insert(@Valid @RequestBody Tarifa tarifa){
        try{
            return new FunesoftResponseDTO(true, tarifaController.insertUpdateTarifa(tarifa), null, null);
        }catch (Exception exception){
            return new FunesoftResponseDTO(false, null, exception.getMessage(), exception);
        }
    }
    
    @PostMapping(path = "update")
    public ResponseDTO update(@Valid @RequestBody Tarifa tarifa){
        try{
            return new FunesoftResponseDTO(true, tarifaController.insertUpdateTarifa(tarifa), null, null);
        }catch (Exception exception){
            return new FunesoftResponseDTO(false, null, exception.getMessage(), exception);
        }
    }
    
    @PostMapping(path = "delete")
    public ResponseDTO delete(@RequestBody TarifaDTO tarifaDTO){
        try{
            return new FunesoftResponseDTO(true, tarifaController.deleteTarifa(tarifaDTO.getId()), null, null);
        }catch (BusinessException exception){
            return new FunesoftResponseDTO(false, null, exception.getMessage(), exception);
        }
    }
    
}
