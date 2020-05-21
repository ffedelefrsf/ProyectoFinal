package com.funesoft.rest;

import com.funesoft.controller.PagoController;
import com.funesoft.dto.FunesoftResponseDTO;
import com.funesoft.dto.PagoDTO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("pago")
@Api(description = "Servicio para ABM de Pagos.")
public class PagoREST {

    @Autowired
    private PagoController pagoController;

    //INFORMAR UN PAGO DE UN COMPROBANTE DE UN SOCIO
    //INCREMENTA EL SALDO DE UN SOCIO
    @PostMapping("informarPago")
    private FunesoftResponseDTO informarPago(@Valid @RequestBody PagoDTO pagoDTO) {
        try {
            return new FunesoftResponseDTO(
                    true,
                    pagoController.informarPago(pagoDTO),
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

}
