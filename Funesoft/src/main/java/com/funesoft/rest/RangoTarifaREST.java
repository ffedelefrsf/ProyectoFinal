package com.funesoft.rest;

import com.funesoft.controller.RangoTarifaController;
import com.funesoft.dto.FunesoftResponseDTO;
import com.funesoft.model.Tarifa;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rangoTarifa")
@Api(description = "Servicios de ABMC para rangos de tarifas.")
public class RangoTarifaREST {

    @Autowired
    private RangoTarifaController rangoTarifaController;

    @PostMapping(path = "getRango")
    @ApiOperation(value = "Obtiene un listado de todas las tarifas registradas en el sistema con los parámetros establecidos.")
    public FunesoftResponseDTO getAll(@RequestBody Tarifa tarifa){
        try{
            return new FunesoftResponseDTO(true, rangoTarifaController.getRangos(tarifa), null, null);
        }catch (Exception exception){
            return new FunesoftResponseDTO(false, null, exception.getMessage(), exception);
        }
    }

}
