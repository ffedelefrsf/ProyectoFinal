/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.controller;

import com.funesoft.dto.ProvinciaDTO;
import com.funesoft.model.Provincia;
import com.funesoft.repository.ProvinciaRepository;
import com.funesoft.utilities.BusinessException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;

/**
 *
 * @author faust
 */
@Controller
public class ProvinciaController {
    
    @Autowired
    private ProvinciaRepository provinciaRepository;
    
    public List<Provincia> getAllProvincias(final ProvinciaDTO provinciaDTO){
        
        final Provincia provincia = new Provincia(provinciaDTO);
        
        final List<Provincia> provincias = provinciaRepository.findAll(Example.of(provincia));
        
        return provincias;
    }
    
}
