package com.funesoft.rest;

import com.funesoft.controller.ZonaCobradorController;
import com.funesoft.dto.FunesoftResponseDTO;
import com.funesoft.dto.ZonaDTO;
import com.funesoft.model.Zona;
import com.funesoft.model.ZonaCobrador;
import com.funesoft.utilities.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("zonaCobrador")
@Api(description = "Servicios para AMBC de zona cobradores.")
public class ZonaCobradorREST {

    @Autowired
    private ZonaCobradorController zonaCobradorController;

    @PostMapping("get")
    @ApiOperation(value = "Obtiene un listado de zonas-cobradores", response = ZonaCobrador.class)
    public FunesoftResponseDTO get(@RequestBody ZonaCobrador zonaCobrador) {
        try {
            return new FunesoftResponseDTO(
                    true,
                    zonaCobradorController.getZonaCobrador(zonaCobrador),
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
    @ApiOperation(value = "Inserta una nueva zona-cobrador", response = ZonaCobrador.class)
    private FunesoftResponseDTO insertZonaCobrador(@Valid @RequestBody ZonaCobrador zonaCobrador) {
        try {
            return new FunesoftResponseDTO(
                    true,
                    zonaCobradorController.insertZonaCobrador(zonaCobrador),
                    null,
                    null
            );
        } catch (Exception e) {
            return new FunesoftResponseDTO(
                    false,
                    null,
                    "Error al insertar la zona-Cobrador",
                    e
            );
        }
    }

    @PostMapping("update")
    @ApiOperation(value = "Actualiza una zona-Cobrador", response = ZonaCobrador.class)
    private FunesoftResponseDTO updateZonaCobrador(@Valid @RequestBody ZonaCobrador zonaCobrador) {
        try {
            return new FunesoftResponseDTO(
                    true,
                    zonaCobradorController.updateZonaCobrador(zonaCobrador),
                    null,
                    null
            );
        } catch (Exception e) {
            return new FunesoftResponseDTO(
                    false,
                    null,
                    "Error al insertar la zona-Cobrador",
                    e
            );
        }
    }

    @PostMapping("delete")
    @ApiOperation(value = "Elimina una zona", response = Zona.class)
    private FunesoftResponseDTO deleteZona(@Valid @RequestBody ZonaDTO zonaDTO) {
        try {
            return new FunesoftResponseDTO(
                    true,
                    zonaCobradorController.deleteZonaCobrador(zonaDTO.getId()),
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
