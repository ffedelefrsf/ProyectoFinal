package com.funesoft.rest;

import com.funesoft.controller.ComprobanteController;
import com.funesoft.controller.LocalidadController;
import com.funesoft.dto.FunesoftResponseDTO;
import com.funesoft.dto.SocioDTO;
import com.funesoft.model.Cobrador;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("comprobante")
@Api(description = "Servicio para ABM de comprobantes.")
public class ComprobanteREST {

    @Autowired
    private ComprobanteController comprobanteController;

    //GENERACIÓN DE COMPROBANTES MASIVO
    //GENERA UNA INSTANCIA DE COMPROBANTE POR CADA SOCIO CON ESTADO 1 - 'ALTA'
    //DECREMENTA EL SALDO DE UN SOCIO
    @PostMapping("generarComprobantesMasivos")
    private FunesoftResponseDTO generarComprobanteMasivos() {
        try {
            return new FunesoftResponseDTO(
                    true,
                    comprobanteController.generarComprobantesMasivos(),
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

    //GENERACIÓN DE COMPROBANTE PARA UN SOCIO
    //DECREMENTA EL SALDO DE UN SOCIO
    @PostMapping("generarComprobante")
    private FunesoftResponseDTO generarComprobante(@Valid @RequestBody SocioDTO socioDTO) {
        try {
            return new FunesoftResponseDTO(
                    true,
                    comprobanteController.generarComprobante(socioDTO.getId()),
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

    //GET ALL COMPROBANTES PARA UN MES ACTUAL
    //SE PASA EL MES POR PARAMETRO Y RECUEPRA TODOS LOS QUE COINCIDA ESE MES CON LA MES(FECHA_ALTA)

    //GET ONE COMPROBANTE
    //PARA UNA POSIBLE REIMPRESIÓN. INPUT MES(PERIODO) IDSOCIO

    //GET INFO PARA POBLAR UN COMPROBANTE A IMPRIMIR
    //ESTE SERVICIO SERÁ CONSUMIDO POR ALGÚN FRAMEWORK PARA LA GENERACIÓN DE UN PDF
    //SE PODRÁ PASAR UNA LISTA DE SOCIOS SIEMPRE Y CUANDO YA ESTÉ GENERADO EL COMPROBANTE PARA LOS MISMOS.
    //DEVOLVERÁ UN DTO 

    //ELIMINAR UN COMPROBANTE

}
