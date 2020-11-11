package com.funesoft.rest;

import com.funesoft.controller.ComprobanteController;
import com.funesoft.controller.LocalidadController;
import com.funesoft.dto.FunesoftResponseDTO;
import com.funesoft.dto.SocioDTO;
import com.funesoft.model.Cobrador;
import com.funesoft.model.Comprobante;
import com.funesoft.repository.ComprobanteRepository;
import io.swagger.annotations.Api;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.*;

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

    //GET ALL COMPROBANTES DADO UN SOCIO
    @PostMapping("getAll")
    private FunesoftResponseDTO getAll(@RequestBody Comprobante comprobante) {
        try {
            return new FunesoftResponseDTO(
                    true,
                    comprobanteController.getAll(comprobante),
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

    //GET ONE COMPROBANTE
    //PARA UNA POSIBLE REIMPRESIÓN. INPUT MES(PERIODO) IDSOCIO

    //GET ALL COMPROBANTES PAGADOS (LOS QUE TENGAN UNA RELACIÓN CON PAGOS)

    //ELIMINAR UN COMPROBANTE

    @GetMapping("generarPDF")
    public FunesoftResponseDTO generarReporte(HttpServletResponse response) {
        try {
            return new FunesoftResponseDTO(
                    true,
                    comprobanteController.generateReport(response),
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
