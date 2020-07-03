/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.controller;

import com.funesoft.dto.EnfermedadDTO;
import com.funesoft.model.Enfermedad;
import com.funesoft.repository.EnfermedadRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;

/**
 *
 * @author faust
 */
@Controller
public class EnfermedadController {
    
    @Autowired
    private EnfermedadRepository enfermedadRepository;
    
    public List<Enfermedad> getAllEnfermedades(EnfermedadDTO enfermedadDTO){
        return enfermedadRepository.findAll(Example.of(new Enfermedad(enfermedadDTO)));
    }
}
