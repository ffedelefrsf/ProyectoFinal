/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.rest;

import com.funesoft.controller.ServicioController;
import com.funesoft.dto.FunesoftResponseDTO;
import com.funesoft.dto.ServicioDTO;
import com.funesoft.model.Servicio;
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
@RequestMapping("servicio")
@Api(description = "Servicios de ABMC para servicios.")
public class ServicioREST {
    
    @Autowired
    private ServicioController servicioController;
    
    
    @PostMapping(path = "getAll")
    @ApiOperation(value = "Obtiene un listado de todos los servicios registrados en el sistema con los parámetros establecidos.")
    public FunesoftResponseDTO getAll(@RequestBody Servicio servicio){
        try{
            return new FunesoftResponseDTO(true, servicioController.findAllServicios(servicio), null, null);
        } catch (Exception exception){
            return new FunesoftResponseDTO(false, null, exception.getMessage(), exception);
        }
    }
    
    @PostMapping(path = "insert")
    @ApiOperation(value = "Inserta un servicio en el sistema.")
    public FunesoftResponseDTO insert(@Valid @RequestBody Servicio servicio){
        try{
            return new FunesoftResponseDTO(true, servicioController.insertServicio(servicio), null, null);
        } catch (Exception exception){
            return new FunesoftResponseDTO(false, null, exception.getMessage(), exception);
        }
    }
    
    @PostMapping(path = "update")
    @ApiOperation(value = "Actualiza un servicio en el sistema.")
    public FunesoftResponseDTO update(@Valid @RequestBody Servicio servicio){
        try{
            return new FunesoftResponseDTO(true, servicioController.updateServicio(servicio), null, null);
        } catch (BusinessException businessException){
            return new FunesoftResponseDTO(false, null, businessException.getMessage(), businessException);
        }
    }
    
    @PostMapping(path = "delete")
    @ApiOperation(value = "Elimina un servicio en el sistema.")
    public FunesoftResponseDTO delete(@RequestBody ServicioDTO servicioDTO){
        try{
            return new FunesoftResponseDTO(true, servicioController.deleteServicio(servicioDTO.getId()), null, null);
        } catch (BusinessException exception){
            return new FunesoftResponseDTO(false, null, exception.getMessage(), exception);
        }
    }
    
}
