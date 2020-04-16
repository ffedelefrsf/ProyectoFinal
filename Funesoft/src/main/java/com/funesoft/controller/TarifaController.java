/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.controller;

import com.funesoft.dto.TarifaDTO;
import com.funesoft.model.Tarifa;
import com.funesoft.repository.TarifaRepository;
import com.funesoft.utilities.BusinessException;
import java.util.List;
import java.util.NoSuchElementException;
import javax.persistence.NoResultException;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;

/**
 *
 * @author faust
 */
@Controller
public class TarifaController {
    
    @Autowired
    private TarifaRepository tarifaRepository;
    
    public List<Tarifa> getAllTarifas(final TarifaDTO tarifaDTO){
        
        final Tarifa tarifa = new Tarifa(tarifaDTO);
        
        final List<Tarifa> tarifas = tarifaRepository.findAll(Example.of(tarifa));
        
        return tarifas;
    }
    
    public Tarifa insertUpdateTarifa(@NotNull final Tarifa tarifa) throws BusinessException{
        try{
            return tarifaRepository.save(tarifa);
        }catch (Exception exception){
            throw new BusinessException("La tarifa especificada no existe.");
        }
    }
    
    public Integer deleteTarifa(@NotNull final Integer idTarifa) throws BusinessException{
        final Tarifa tarifa;
        try{
            tarifa = tarifaRepository.findById(idTarifa).get();
            tarifaRepository.delete(tarifa);
            return idTarifa;
        }catch (NoSuchElementException exception){
            throw new BusinessException("La tarifa especificada no existe.");
        }
    }
    
}
