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
public class PlanController {
    
    @Autowired
    private PlanRepository planRepository;
    
    public List<Plan> getAllPlanes(PlanDTO planDTO){
        
        final Plan plan = new Plan(planDTO);
        
        final List<Plan> planes;
        final Integer pagina = planDTO.getPagina(), cantidad = planDTO.getCantidad();
        final String sortField = planDTO.getSortField();
        final Boolean sortWay = planDTO.getSortWay();
        if (pagina != null && cantidad != null){
            if (sortField != null && sortWay != null){
                if (sortWay){
                    planes = planRepository.findAll(Example.of(plan), PageRequest.of(pagina, cantidad, Sort.by(sortField).ascending())).getContent();
                }else{
                    planes = planRepository.findAll(Example.of(plan), PageRequest.of(pagina, cantidad, Sort.by(sortField).descending())).getContent();
                }
            }else{
                planes = planRepository.findAll(Example.of(plan), PageRequest.of(pagina, cantidad)).getContent();
            }
        }else{
            planes = planRepository.findAll(Example.of(plan));
        }
        
        return planes;
        
    }
    
    public Plan insertPlan(@NotNull final Plan plan) throws BusinessException{
        if (!planRepository.findById(plan.getId()).isPresent()){
            try{
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
                return planRepository.save(plan);
            }catch(Exception exception){
                throw new BusinessException(exception.getMessage());
            }
        }else{
            throw new BusinessException("El plan especificado no existe.");
        }
    }
    
    public Integer deletePlan(@NotNull final Integer idPlan) throws BusinessException{
        if (planRepository.findById(idPlan).isPresent()){
            try{
                planRepository.deleteById(idPlan);
                return idPlan;
            }catch (Exception exception){
                throw new BusinessException(exception.getMessage());
            }
        }else{
            throw new BusinessException("El plan especificado no existe.");
        }
    }
    
}
