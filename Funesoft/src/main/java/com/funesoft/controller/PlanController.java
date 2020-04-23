/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.controller;

import com.funesoft.dto.PlanDTO;
import com.funesoft.model.Plan;
import com.funesoft.repository.PlanRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;

/**
 *
 * @author faust
 */
@Controller
public class PlanController {
    
    @Autowired
    private PlanRepository planRepository;
    
    public List<Plan> getAllPlanes(PlanDTO planDTO){
        return planRepository.findAll(Example.of(new Plan(planDTO)));
    }
    
}
