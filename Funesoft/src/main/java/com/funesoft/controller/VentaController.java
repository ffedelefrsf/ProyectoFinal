/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.controller;

import com.funesoft.model.Venta;
import com.funesoft.repository.VentaRepository;
import com.funesoft.utilities.BusinessException;
import com.funesoft.utilities.CurrentUser;
import java.util.List;
import java.util.Optional;
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

    
}
