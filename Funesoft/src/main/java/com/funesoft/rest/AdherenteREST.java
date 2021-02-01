/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.rest;

import com.funesoft.controller.AdherenteController;
import com.funesoft.dto.AdherenteBajaDTO;
import com.funesoft.dto.AdherenteDTO;
import com.funesoft.dto.FunesoftResponseDTO;
import com.funesoft.dto.GetAdherenteBySocioDNIRequestDTO;
import com.funesoft.dto.RemoveAllBySocioDTO;
import com.funesoft.model.Adherente;
import io.swagger.annotations.ApiOperation;
import javax.persistence.EntityManager;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    
    @PostMapping("getAll")
    @ApiOperation(value = "Obtiene un listado de todos los adherentes registrados en el sistema.")
    public FunesoftResponseDTO get(@RequestBody Adherente adherente) {
        try{
            return new FunesoftResponseDTO(true, adherenteController.getAll(adherente), null, null);
        }catch (Exception exception){
            return new FunesoftResponseDTO(false, null, exception.getMessage(), exception);
        }
    }
    
    @PostMapping("getAllOrderedBySocio")
    @ApiOperation(value = "Obtiene un listado de todos los adherentes registrados en el sistema ordenados por DNI de su respectivo socio.")
    public FunesoftResponseDTO getAllOrderedBySocio(@RequestBody GetAdherenteBySocioDNIRequestDTO getAdherenteBySocioDNIRequestDTO) {
        try{
            return new FunesoftResponseDTO(true, adherenteController.getAllOrderedBySocio(getAdherenteBySocioDNIRequestDTO.getDniSocio()), null, null);
        }catch (Exception exception){
            return new FunesoftResponseDTO(false, null, exception.getMessage(), exception);
        }
    }
    
    @PostMapping("insert")
    @ApiOperation(value = "Inserta un adherente", response = AdherenteDTO.class)
    public FunesoftResponseDTO insertAdherente(@Valid @RequestBody AdherenteDTO adherenteDTO) {
        try {
            return new FunesoftResponseDTO(true, adherenteController.insertAdherente(adherenteDTO), null, null);
        } catch (Exception e) {
            return new FunesoftResponseDTO(false, null, "Error al insertar el adherente - DNI: " + adherenteDTO.getDni(),e);
        }
    }
    
    @PostMapping("update")
    @ApiOperation(value = "Actualiza un adherente", response = Adherente.class)
    public FunesoftResponseDTO updateAdherente(@Valid @RequestBody Adherente adherente) {
        try {
            return new FunesoftResponseDTO(true, adherenteController.updateAdherente(adherente), null, null);
        } catch (Exception e) {
            return new FunesoftResponseDTO(false, null, "Error al insertar el adherente - DNI: " + adherente.getDni(), e);
        }
    }
    
    @PostMapping("delete")
    @ApiOperation(value = "Elimina un adherente", response = Adherente.class)
    public FunesoftResponseDTO deleteAdherente(@Valid @RequestBody AdherenteBajaDTO adherenteBajaDTO) {
        try {
            return new FunesoftResponseDTO(true, adherenteController.deleteAdherente(adherenteBajaDTO), null, null);
        } catch (Exception e) {
            return new FunesoftResponseDTO(false, null, "Error al eliminar el adherente - ID: " + adherenteBajaDTO.getIdAdherente(), e);
        }
    }
    
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
