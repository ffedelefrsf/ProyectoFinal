package com.funesoft.rest;

import com.funesoft.controller.EstadisticaController;
import com.funesoft.controller.PagoController;
import com.funesoft.dto.FunesoftResponseDTO;
import com.funesoft.dto.PagoDTO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("estadistica")
@Api(description = "Servicio para consumo de métricas del negocio.")
public class EstadisticaREST {

    @Autowired
    private EstadisticaController estadisticaController;

    @GetMapping("getAll")
    public FunesoftResponseDTO getAll() {
        try {
            return new FunesoftResponseDTO(
                    true,
                    estadisticaController.getEstadisticasHeader(),
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
