package com.funesoft.rest;

import com.funesoft.controller.ZonaController;
import com.funesoft.dto.FunesoftResponseDTO;
import com.funesoft.dto.ResponseDTO;
import com.funesoft.dto.ZonaDTO;
import com.funesoft.model.Zona;
import com.funesoft.utilities.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("zona")
@Api(description = "Servicios para AMBC de zonas.")
public class ZonaREST {

    @Autowired
    private ZonaController zonaController;

    @PostMapping("get")
    @ApiOperation(value = "Obtiene un listado de zonas")
    public ResponseDTO get(@RequestBody Zona zona) {
        try {
            return new FunesoftResponseDTO(
                    true,
                    zonaController.getZonas(zona),
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
    @ApiOperation(value = "Inserta una nueva zona", response = Zona.class)
    private ResponseDTO insertZona(@Valid @RequestBody Zona zona) {
        try {
            return new FunesoftResponseDTO(
                    true,
                    zonaController.insertZona(zona),
                    null,
                    null
            );
        } catch (Exception e) {
            return new FunesoftResponseDTO(
                    false,
                    null,
                    "Error al insertar la zona - Número: " + zona.getNroZona(),
                    e
            );
        }
    }

    @PostMapping("update")
    @ApiOperation(value = "Actualiza una zona", response = Zona.class)
    private ResponseDTO updateZona(@Valid @RequestBody Zona zona) {
        try {
            return new FunesoftResponseDTO(
                    true,
                    zonaController.updateZona(zona),
                    null,
                    null
            );
        } catch (Exception e) {
            return new FunesoftResponseDTO(
                    false,
                    null,
                    "Error al insertar la zona - Número: " + zona.getNroZona(),
                    e
            );
        }
    }

    @PostMapping("delete")
    @ApiOperation(value = "Elimina una zona", response = Zona.class)
    private ResponseDTO deleteZona(@Valid @RequestBody ZonaDTO zonaDTO) {
        try {
            return new FunesoftResponseDTO(
                    true,
                    zonaController.deleteZona(zonaDTO.getIdZona()),
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
