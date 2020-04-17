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
import com.funesoft.dto.ZonaDTO;
import com.funesoft.model.Socio;
import com.funesoft.model.Zona;
import com.funesoft.utilities.BusinessException;
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
 * @author faust
 */
@RestController
@RequestMapping("socio")
@Api(description = "Servicios para AMBC de socios.")
public class SocioREST {

    @Autowired
    private SocioController socioController;

    @PostMapping("get")
    @ApiOperation(value = "Obtiene un listado de todos los socios registrados en el sistema.")
    public ResponseDTO get(@RequestBody Socio socio) {
        try {
            return new FunesoftResponseDTO(
                    true,
                    socioController.getSocios(socio),
                    null,
                    null
            );
        } catch (Exception e) {
            return new FunesoftResponseDTO(
                    false,
                    null,
                    "Error en la recuperación",
                    e
            );
        }
    }

    @PostMapping("insert")
    @ApiOperation(value = "Inserta un socio", response = SocioDTO.class)
    private ResponseDTO insertSocio(@Valid @RequestBody SocioDTO socioDTO) {
        try {
            return new FunesoftResponseDTO(
                    true,
                    socioController.insertSocio(socioDTO),
                    null,
                    null
            );
        } catch (Exception e) {
            return new FunesoftResponseDTO(
                    false,
                    null,
                    "Error al insertar el socio - DNI: " + socioDTO.getDni(),
                    e
            );
        }
    }

    @PostMapping("update")
    @ApiOperation(value = "Actualiza un socio", response = SocioDTO.class)
    private ResponseDTO updateSocio(@Valid @RequestBody Socio socio) {
        try {
            return new FunesoftResponseDTO(
                    true,
                    socioController.updateSocio(socio),
                    null,
                    null
            );
        } catch (Exception e) {
            return new FunesoftResponseDTO(
                    false,
                    null,
                    "Error al insertar el socio - DNI: " + socio.getDni(),
                    e
            );
        }
    }

    @PostMapping("delete")
    @ApiOperation(value = "Cambia el estado del socio a baja", response = Zona.class)
    private ResponseDTO deleteSocio(@Valid @RequestBody Socio socio) {
        try {
            return new FunesoftResponseDTO(
                    true,
                    socioController.deleteSocio(idSocio, idMotivoBaja),
                    null,
                    null
            );
        } catch (BusinessException be) {
            return new FunesoftResponseDTO(
                    false,
                    null,
                    be.getMessage(),
                    be
            );
        } catch (Exception e) {
            return new FunesoftResponseDTO(
                    false,
                    null,
                    "Error al eliminar la zona",
                    e
            );
        }
    }

}
