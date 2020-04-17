/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.controller;

import com.funesoft.dto.TarifaDTO;
import com.funesoft.model.Plan;
import com.funesoft.model.Tarifa;
import com.funesoft.repository.TarifaRepository;
import com.funesoft.utilities.BusinessException;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
        
        final List<Tarifa> tarifas;
        final Integer pagina = tarifaDTO.getPagina(), cantidad = tarifaDTO.getCantidad();
        final String sortField = tarifaDTO.getSortField();
        final Boolean sortWay = tarifaDTO.getSortWay();
        if (pagina != null && cantidad != null){
            if (sortField != null && sortWay != null){
                if (sortWay){
                    tarifas = tarifaRepository.findAll(Example.of(tarifa), PageRequest.of(pagina, cantidad, Sort.by(sortField).ascending())).getContent();
                }else{
                    tarifas = tarifaRepository.findAll(Example.of(tarifa), PageRequest.of(pagina, cantidad, Sort.by(sortField).descending())).getContent();
                }
            }else{
                tarifas = tarifaRepository.findAll(Example.of(tarifa), PageRequest.of(pagina, cantidad)).getContent();
            }
        }else{
            tarifas = tarifaRepository.findAll(Example.of(tarifa));
        }
        
        return tarifas;
    }
    
    public Tarifa insertTarifa(@NotNull final Tarifa tarifa) throws BusinessException{
        if (!tarifaRepository.findById(tarifa.getId()).isPresent()){
            try{
                return tarifaRepository.save(tarifa);
            }catch(Exception exception){
                throw new BusinessException(exception.getMessage());
            }
        }else{
            throw new BusinessException("La tarifa especificada ya existe.");
        }
    }
    
    public Tarifa updateTarifa(@NotNull final Tarifa tarifa) throws BusinessException{
        if (tarifaRepository.findById(tarifa.getId()).isPresent()){
            try{
                return tarifaRepository.save(tarifa);
            }catch(Exception exception){
                throw new BusinessException(exception.getMessage());
            }
        }else{
            throw new BusinessException("La tarifa especificada no existe.");
        }
    }
    
    public Integer deleteTarifa(@NotNull final Integer idTarifa) throws BusinessException{
        if (tarifaRepository.findById(idTarifa).isPresent()){
            try{
                tarifaRepository.deleteById(idTarifa);
                return idTarifa;
            }catch (Exception exception){
                throw new BusinessException(exception.getMessage());
            }
        }else{
            throw new BusinessException("La tarifa especificada no existe.");
        }
    }
    
}
