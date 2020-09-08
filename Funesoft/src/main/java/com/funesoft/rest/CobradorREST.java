package com.funesoft.rest;

import com.funesoft.controller.CobradorController;
import com.funesoft.controller.LocalidadController;
import com.funesoft.dto.CobradorDTO;
import com.funesoft.dto.FunesoftResponseDTO;
import com.funesoft.dto.LocalidadDTO;
import com.funesoft.dto.ZonaDTO;
import com.funesoft.model.Cobrador;
import com.funesoft.model.Zona;
import com.funesoft.utilities.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("cobrador")
@Api(description = "Servicio para ABM de un cobrador.")
public class CobradorREST {

    @Autowired
    private CobradorController cobradorController;

    @PostMapping("getAll")
    public FunesoftResponseDTO getAll(@RequestBody Cobrador cobrador){
        try {
            return new FunesoftResponseDTO(
                    true,
                    cobradorController.getAllCobradores(cobrador),
                    null,
                    null
            );
        } catch (Exception exception) {
            return new FunesoftResponseDTO(
                    false,
                    null,
                    exception.getMessage(),
                    exception
            );
        }
    }

    @PostMapping("insert")
    private FunesoftResponseDTO insertCobrador(@Valid @RequestBody Cobrador cobrador) {
        try {
            return new FunesoftResponseDTO(
                    true,
                    cobradorController.insertCobrador(cobrador),
                    null,
                    null
            );
        } catch (Exception e) {
            return new FunesoftResponseDTO(
                    false,
                    null,
                    "Error al insertar el cobrador - DNI: " + cobrador.getDni(),
                    e
            );
        }
    }

    @PostMapping("update")
    private FunesoftResponseDTO updateCobrador(@Valid @RequestBody Cobrador cobrador) {
        try {
            return new FunesoftResponseDTO(
                    true,
                    cobradorController.updateCobrador(cobrador),
                    null,
                    null
            );
        } catch (Exception e) {
            return new FunesoftResponseDTO(
                    false,
                    null,
                    "Error al insertar el cobrador - Apellido: " + cobrador.getDni(),
                    e
            );
        }
    }

    @PostMapping("delete")
    private FunesoftResponseDTO deleteCobrador(@Valid @RequestBody CobradorDTO cobradorDTO) {
        try {
            return new FunesoftResponseDTO(
                    true,
                    cobradorController.deleteCobrador(cobradorDTO.getId()),
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
                    "Error al eliminar el cobrador",
                    e
            );
        }
    }

}
