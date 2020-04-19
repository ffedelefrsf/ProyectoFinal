/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.rest;

import com.funesoft.controller.AdherenteController;
import com.funesoft.dto.FunesoftResponseDTO;
import com.funesoft.dto.RemoveAllBySocioDTO;
import javax.persistence.EntityManager;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author faust
 */
@RestController
@RequestMapping("adherente")
public class AdherenteREST {
    
    @Autowired
    private AdherenteController adherenteController;
    
    @Autowired
    private EntityManager entityManager;
    
    @PostMapping (path = "removeAllBySocio")
    public FunesoftResponseDTO removeAllBySocio(@Valid @RequestBody RemoveAllBySocioDTO removeAllBySocioDTO){
        try{
            return new FunesoftResponseDTO(true, adherenteController.removeAllBySocio(removeAllBySocioDTO), null, null);
        }catch (Exception exception){
            return new FunesoftResponseDTO(false, null, exception.getMessage(), exception);
        }
    }
    
    @GetMapping (path = "currentUserJDBC")
    public String getUser(){
        return entityManager.createNativeQuery("SELECT CURRENT_USER()").getSingleResult().toString();
    }
    
}
