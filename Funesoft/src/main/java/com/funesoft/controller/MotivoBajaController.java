/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.controller;

import com.funesoft.model.MotivoBaja;
import com.funesoft.repository.MotivoBajaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author faust
 */
@Controller
public class MotivoBajaController {
    
    @Autowired
    private MotivoBajaRepository motivoBajaRepository;
    
    public List<MotivoBaja> getAll(){
        return motivoBajaRepository.findAll();
    }
    
}
