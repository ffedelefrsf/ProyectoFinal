/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.repository;

import com.funesoft.model.CoberturaEdad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author faust
 */
public interface CoberturaEdadRepository extends JpaRepository<CoberturaEdad, Integer>{
    
    @Query("SELECT CE FROM CoberturaEdad CE WHERE CE.edadDesde <= :edad AND CE.edadHasta >= :edad")
    public CoberturaEdad findByRangoEdad(Short edad);
    
}
