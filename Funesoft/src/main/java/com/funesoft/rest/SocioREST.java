/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.rest;

import com.funesoft.controller.SocioController;
import com.funesoft.dto.request.socio.SocioInsertRequestDTO;
import com.funesoft.dto.response.error.ErrorResponseDTO;
import com.funesoft.dto.response.socio.SocioInsertResponseDTO;
import com.funesoft.model.Socio;
import com.funesoft.utilities.ErrorManagerController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    
    @Autowired
    private ErrorManagerController errorManagerController;
    
    @GetMapping("getAll")
    @ApiOperation(value = "Obtiene un listado de todos los socios registrados en el sistema.")
    public List<Socio> getAll(){
        return socioController.getAllSocios();
    }
    
    @PostMapping("insert")
    @ApiOperation(value = "Inserta un nuevo socio.", response = SocioInsertResponseDTO.class)
    private ErrorResponseDTO insertSocio(@RequestBody SocioInsertRequestDTO socioInsertRequestDTO){
        // VALIDO LOS PARÁMETROS
        final ErrorResponseDTO errorResponseDTO = errorManagerController.checkParameters(Arrays.asList(socioInsertRequestDTO.getDni(), socioInsertRequestDTO.getApellido(), socioInsertRequestDTO.getNombre(), socioInsertRequestDTO.getDireccion(), socioInsertRequestDTO.getTelefono(), socioInsertRequestDTO.getEmail(), socioInsertRequestDTO.getSexo(), socioInsertRequestDTO.getFechaNacimiento(), socioInsertRequestDTO.getUsuarioAlta(), socioInsertRequestDTO.getSaldo(), socioInsertRequestDTO.getIdTarifa(), socioInsertRequestDTO.getIdZona(), socioInsertRequestDTO.getIdLocalidad(), socioInsertRequestDTO.getIdObraSocial()));
        if (errorResponseDTO != null){
            return errorResponseDTO;
        }
        
        // INSERTO EL SOCIO
        final Socio socio = new Socio(socioInsertRequestDTO.getDni(), socioInsertRequestDTO.getApellido(), socioInsertRequestDTO.getNombre(), socioInsertRequestDTO.getDireccion(), socioInsertRequestDTO.getTelefono(), socioInsertRequestDTO.getEmail(), socioInsertRequestDTO.getSexo(), socioInsertRequestDTO.getFechaNacimiento(), socioInsertRequestDTO.getFechaCobertura(), socioInsertRequestDTO.getUsuarioAlta(), socioInsertRequestDTO.getSaldo(), socioInsertRequestDTO.getIdTarifa(), socioInsertRequestDTO.getIdZona(), socioInsertRequestDTO.getIdLocalidad(), socioInsertRequestDTO.getIdObraSocial());
        final SocioInsertResponseDTO socioInsertResponseDTO;
        socioInsertResponseDTO = new SocioInsertResponseDTO(socioController.insertSocio(socio));
        /*
        }catch(SQLIntegrityConstraintViolationException exception){
            // NUNCA SE LLAMA A ESTO, PERO PARA TENER DE EJEMPLO
            return new ErrorResponseDTO(ErrorManagerController.CLAVE_DUPLICADA);
        }*/
        return socioInsertResponseDTO;
    }
}
