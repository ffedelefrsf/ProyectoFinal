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
        return localidadRepository.findAll(Example.of(new Localidad(localidadDTO)));
    }
    
    public List<String> getAllNombres(final LocalidadDTO localidadDTO){
        return localidadRepository.findNombreByProvinciaId(localidadDTO.getProvincia().getId());
    }
    
}
