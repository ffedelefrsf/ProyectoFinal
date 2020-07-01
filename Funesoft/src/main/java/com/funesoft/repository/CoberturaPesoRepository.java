/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.repository;

import com.funesoft.model.CoberturaPeso;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author faust
 */
public interface CoberturaPesoRepository extends JpaRepository<CoberturaPeso, Integer>{
    
    public CoberturaPeso findByDescripcion(String descripcion);
    
}
