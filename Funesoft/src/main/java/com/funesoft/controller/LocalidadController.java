/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.controller;

import com.funesoft.dto.LocalidadDTO;
import com.funesoft.model.Localidad;
import com.funesoft.repository.LocalidadRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;

/**
 *
 * @author faust
 */
@Controller
public class LocalidadController {
    
    @Autowired
    private LocalidadRepository localidadRepository;
    
    public List<Localidad> getAllLocalidades(final LocalidadDTO localidadDTO){
        
        final Localidad localidad = new Localidad(localidadDTO);
        
        final List<Localidad> localidades;
        final Integer pagina = localidadDTO.getPagina(), cantidad = localidadDTO.getCantidad();
        final String sortField = localidadDTO.getSortField();
        final Boolean sortWay = localidadDTO.getSortWay();
        if (pagina != null && cantidad != null){
            if (sortField != null && sortWay != null){
                if (sortWay){
                    localidades = localidadRepository.findAll(Example.of(localidad), PageRequest.of(pagina, cantidad, Sort.by(sortField).ascending())).getContent();
                }else{
                    localidades = localidadRepository.findAll(Example.of(localidad), PageRequest.of(pagina, cantidad, Sort.by(sortField).descending())).getContent();
                }
            }else{
                localidades = localidadRepository.findAll(Example.of(localidad), PageRequest.of(pagina, cantidad)).getContent();
            }
        }else{
            localidades = localidadRepository.findAll(Example.of(localidad));
        }
        
        return localidades;
    }
    
}
