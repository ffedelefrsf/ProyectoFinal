/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.repository;

import com.funesoft.model.CoberturaEnfermedad;
import com.funesoft.model.Enfermedad;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author faust
 */
public interface CoberturaEnfermedadRepository extends JpaRepository<CoberturaEnfermedad, Integer>{
    
    public CoberturaEnfermedad findByEnfermedad(Enfermedad enfermedad);
    
}
