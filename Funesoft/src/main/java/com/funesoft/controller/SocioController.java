/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.controller;

import com.funesoft.dto.SocioDTO;
import com.funesoft.model.Socio;
import com.funesoft.repository.SocioRepository;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author faust
 */
@Controller
public class SocioController {
    
    @Autowired
    private SocioRepository socioRepository;

    @Autowired
    private CoberturaController coberturaController;

    public List<Socio> getAllSocios (){
        return socioRepository.findAll();
    }

    public Socio insertSocio (SocioDTO socioDTO){

        //CALCULO LA COBERTURA
        socioDTO.setFechaCobertura(coberturaController.calculoCobertura(socioDTO.getFechaNacimiento()));
        Socio socio = new Socio(socioDTO);
        return socioRepository.save(socio);

    }

}
