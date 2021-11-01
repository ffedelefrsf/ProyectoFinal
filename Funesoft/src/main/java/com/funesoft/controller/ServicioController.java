/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.controller;

import com.funesoft.model.Servicio;
import com.funesoft.repository.ServicioRepository;
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
public class ServicioController {
    
    @Autowired
    private ServicioRepository servicioRepository;

    public List<Servicio> findAllServicios(final Servicio servicio){
        return servicioRepository.findAll(Example.of(servicio));
    }

    public Servicio insertServicio (@NotNull Servicio servicio){
        servicio.setUsuarioModifica(CurrentUser.getInstance());
        return servicioRepository.save(servicio);
    }

    public Servicio updateServicio (@NotNull Servicio servicio) throws BusinessException {
        Optional<Servicio> servicioUpdate = servicioRepository.findById(servicio.getId());
        if(!servicioUpdate.isPresent()){
            throw new BusinessException("El servicio informado no existe");
        }
        servicioUpdate.get().setUsuarioModifica(CurrentUser.getInstance());
        return servicioRepository.save(servicioUpdate.get());
    }

    public Servicio deleteServicio (@NotNull Integer idServicio) throws BusinessException {
        Optional<Servicio> servicioDelete = servicioRepository.findById(idServicio);
        if(!servicioDelete.isPresent()){
            throw new BusinessException("El servicio informado no existe");
        }
        servicioDelete.get().setUsuarioModifica(CurrentUser.getInstance());
        servicioRepository.delete(servicioRepository.save(servicioDelete.get()));
        return servicioDelete.get();
    }

    
}
