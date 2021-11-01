/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.rest;

import com.funesoft.controller.VentaController;
import com.funesoft.dto.FunesoftResponseDTO;
import com.funesoft.dto.VentaDTO;
import com.funesoft.model.Venta;
import com.funesoft.utilities.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author faust
 */
@RestController
@RequestMapping("venta")
@Api(description = "Servicios de ABMC para ventas.")
public class VentaREST {
    
    @Autowired
    private VentaController ventaController;
    
    
    @PostMapping(path = "getAll")
    @ApiOperation(value = "Obtiene un listado de todas las ventas registradas en el sistema con los parámetros establecidos.")
    public FunesoftResponseDTO getAll(@RequestBody Venta venta){
        try{
            return new FunesoftResponseDTO(true, ventaController.findAllVentas(venta), null, null);
        } catch (Exception exception){
            return new FunesoftResponseDTO(false, null, exception.getMessage(), exception);
        }
    }
    
    @PostMapping(path = "insert")
    @ApiOperation(value = "Inserta una venta en el sistema.")
    public FunesoftResponseDTO insert(@Valid @RequestBody Venta venta){
        try{
            return new FunesoftResponseDTO(true, ventaController.insertVenta(venta), null, null);
        } catch (Exception exception){
            return new FunesoftResponseDTO(false, null, exception.getMessage(), exception);
        }
    }
    
    @PostMapping(path = "update")
    @ApiOperation(value = "Actualiza una venta en el sistema.")
    public FunesoftResponseDTO update(@Valid @RequestBody Venta venta){
        try{
            return new FunesoftResponseDTO(true, ventaController.updateVenta(venta), null, null);
        } catch (BusinessException businessException){
            return new FunesoftResponseDTO(false, null, businessException.getMessage(), businessException);
        }
    }
    
    @PostMapping(path = "delete")
    @ApiOperation(value = "Elimina una venta en el sistema.")
    public FunesoftResponseDTO delete(@RequestBody VentaDTO ventaDTO){
        try{
            return new FunesoftResponseDTO(true, ventaController.deleteVenta(ventaDTO.getId()), null, null);
        } catch (BusinessException exception){
            return new FunesoftResponseDTO(false, null, exception.getMessage(), exception);
        }
    }
    
}
