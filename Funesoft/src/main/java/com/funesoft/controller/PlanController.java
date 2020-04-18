/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.controller;

import com.funesoft.dto.PlanDTO;
import com.funesoft.model.Plan;
import com.funesoft.repository.PlanRepository;
import com.funesoft.utilities.BusinessException;
import com.funesoft.utilities.CurrentUser;
import java.util.List;
import java.util.NoSuchElementException;
import javax.validation.constraints.NotNull;
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
    
    public Plan insertPlan(@NotNull final Plan plan) throws BusinessException{
        if (!planRepository.findById(plan.getId()).isPresent()){
            try{
                plan.setUsuarioModifica(CurrentUser.getInstance());
                return planRepository.save(plan);
            }catch(Exception exception){
                throw new BusinessException(exception.getMessage());
            }
        }else{
            throw new BusinessException("El plan especificado ya existe.");
        }
    }
    
    public Plan updatePlan(@NotNull final Plan plan) throws BusinessException{
        if (planRepository.findById(plan.getId()).isPresent()){
            try{
                plan.setUsuarioModifica(CurrentUser.getInstance());
                return planRepository.save(plan);
            }catch(Exception exception){
                throw new BusinessException(exception.getMessage());
            }
        }else{
            throw new BusinessException("El plan especificado no existe.");
        }
    }
    
    public Plan deletePlan(@NotNull final Integer idPlan) throws BusinessException{
        final Plan plan;
        try{
            plan = planRepository.findById(idPlan).get();
            try{
                plan.setUsuarioModifica(CurrentUser.getInstance());
                planRepository.save(plan);
                planRepository.deleteById(idPlan);
                return plan;
            }catch (Exception exception){
                throw new BusinessException(exception.getMessage());
            }
        }catch (NoSuchElementException nseex){
            throw new BusinessException("El plan especificado no existe.");
        }
    }
    
}
