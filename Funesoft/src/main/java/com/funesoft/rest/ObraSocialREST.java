/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.rest;

import com.funesoft.controller.ObraSocialController;
import com.funesoft.dto.FunesoftResponseDTO;
import com.funesoft.model.ObraSocial;
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
@RequestMapping("obraSocial")
@Api(description = "Servicios de ABM para obras sociales")
public class ObraSocialREST {
    
    @Autowired
    private ObraSocialController obraSocialController;
    
    @PostMapping(path = "getAll")
    @ApiOperation(value = "Obtiene un listado de todas las obras sociales registradas en el sistema con los parámetros establecidos.")
    public FunesoftResponseDTO getAll(@RequestBody ObraSocial obraSocial){
        try{
            return new FunesoftResponseDTO(true, obraSocialController.getAllObrasSociales(obraSocial), null, null);
        }catch (Exception exception){
            return new FunesoftResponseDTO(false, null, exception.getMessage(), exception);
        }
    }
    
    @PostMapping(path = "insert")
    @ApiOperation(value = "Inserta una obra social en el sistema.")
    public FunesoftResponseDTO insert(@Valid @RequestBody ObraSocial obraSocial){
        try{
            return new FunesoftResponseDTO(true, obraSocialController.insertObraSocial(obraSocial), null, null);
        }catch (Exception ex){
            return new FunesoftResponseDTO(false, null, ex.getMessage(), ex);
        }
    }
    
    @PostMapping(path = "update")
    @ApiOperation(value = "Actualiza una obra social en el sistema.")
    public FunesoftResponseDTO update(@Valid @RequestBody ObraSocial obraSocial){
        try{
            return new FunesoftResponseDTO(true, obraSocialController.updateObraSocial(obraSocial), null, null);
        }catch (BusinessException businessException){
            return new FunesoftResponseDTO(false, null, businessException.getMessage(), businessException);
        }
    }
    
    @PostMapping(path = "delete")
    @ApiOperation(value = "Elimina una obra social en el sistema.")
    public FunesoftResponseDTO delete(@RequestBody ObraSocial obraSocial){
        try{
            return new FunesoftResponseDTO(true, obraSocialController.deleteObraSocial(obraSocial.getId()), null, null);
        }catch (BusinessException exception){
            return new FunesoftResponseDTO(false, null, exception.getMessage(), exception);
        }
    }
}
