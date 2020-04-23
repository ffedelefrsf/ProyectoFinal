/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.rest;

import com.funesoft.controller.PlanController;
import com.funesoft.dto.FunesoftResponseDTO;
import com.funesoft.dto.PlanDTO;
import com.funesoft.dto.ResponseDTO;
import com.funesoft.model.Plan;
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
@RequestMapping("plan")
@Api(description = "Servicios de ABMC para planes.")
public class PlanREST {
    
    @Autowired
    private PlanController planController;
    
    @PostMapping(path = "getAll")
    @ApiOperation(value = "Obtiene un listado de todos los planes registrados en el sistema con los parámetros establecidos.")
    public FunesoftResponseDTO getAll(@RequestBody PlanDTO planDTO){
        try{
            return new FunesoftResponseDTO(true, planController.getAllPlanes(planDTO), null, null);
        }catch (Exception exception){
            return new FunesoftResponseDTO(false, null, exception.getMessage(), exception);
        }
    }
    
}
