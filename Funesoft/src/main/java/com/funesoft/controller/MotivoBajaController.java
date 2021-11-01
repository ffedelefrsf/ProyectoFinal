/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.controller;

import com.funesoft.model.MotivoBaja;
import com.funesoft.model.Socio;
import com.funesoft.repository.MotivoBajaRepository;
import java.util.List;
import java.util.Optional;

import com.funesoft.repository.SocioRepository;
import com.funesoft.utilities.BusinessException;
import com.funesoft.utilities.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.validation.constraints.NotNull;

/**
 *
 * @author faust
 */
@Controller
public class MotivoBajaController {
    
    @Autowired
    private MotivoBajaRepository motivoBajaRepository;

    @Autowired
    private SocioRepository socioRepository;
    
    public List<MotivoBaja> getAll(){
        return motivoBajaRepository.findAll();
    }

    public MotivoBaja getMotivo (@NotNull Socio socio) throws BusinessException {
        if(!(socioRepository.findById(socio.getId()).isPresent())){
            throw new BusinessException("El socio informado no existe");
        }

        Optional<MotivoBaja> motivoBaja = socioRepository.findMotivoBaja(socio.getId());

        if(!motivoBaja.isPresent()){
            throw new BusinessException("El socio informado no está dado de baja");
        }

        return motivoBaja.get();
    }
    
}
