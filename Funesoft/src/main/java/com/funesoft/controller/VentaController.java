/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.controller;

import com.funesoft.dto.VentasDTO;
import com.funesoft.model.Servicio;
import com.funesoft.model.Venta;
import com.funesoft.repository.VentaRepository;
import com.funesoft.repository.VentasRepository;
import com.funesoft.utilities.BusinessException;
import com.funesoft.utilities.CurrentUser;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;

/**
 *
 * @author faust
 */
@Controller
public class VentaController {
    
    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private VentasRepository ventasRepository;

    @Autowired
    private ServicioController servicioController;

    public List<Venta> findAllVentas(final Venta venta){
        return ventaRepository.findAll(Example.of(venta));
    }

    public Venta insertVenta(@NotNull Venta venta){
        venta.setUsuarioModifica(CurrentUser.getInstance());
        return ventaRepository.save(venta);
    }

    public Venta updateVenta(@NotNull Venta venta) throws BusinessException {
        Optional<Venta> ventaUpdate = ventaRepository.findById(venta.getId());
        if(!ventaUpdate.isPresent()){
            throw new BusinessException("La venta informada no existe");
        }
        ventaUpdate.get().setUsuarioModifica(CurrentUser.getInstance());
        return ventaRepository.save(ventaUpdate.get());
    }

    public Venta deleteVenta(@NotNull Integer idVenta) throws BusinessException {
        Optional<Venta> ventaDelete = ventaRepository.findById(idVenta);
        if(!ventaDelete.isPresent()){
            throw new BusinessException("La venta informada no existe");
        }
        ventaDelete.get().setUsuarioModifica(CurrentUser.getInstance());
        ventaRepository.delete(ventaRepository.save(ventaDelete.get()));
        return ventaDelete.get();
    }

    public List<VentasDTO> findAllVentasDTO(final Venta venta){
        List<Object[]> ventas = ventasRepository.findVentasTotal();
        List<VentasDTO> resultVentas = new ArrayList<>();
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");

        for (Object[] item : ventas) {
            VentasDTO dto = new VentasDTO();
            dto.setId(Integer.valueOf(item[0].toString()));
            dto.setNroVenta(Integer.valueOf(item[1].toString()));
            dto.setDescripcion(item[2].toString());
            dto.setServicio(servicioController.findAllServicios(
                    new Servicio(Integer.valueOf(item[3].toString()))
            ).get(0));
            Date date = new Date(((Timestamp)item[4]).getTime());
            dto.setFecha(format1.format(date));
            resultVentas.add(dto);
        }
        
        return resultVentas;
    }


}
