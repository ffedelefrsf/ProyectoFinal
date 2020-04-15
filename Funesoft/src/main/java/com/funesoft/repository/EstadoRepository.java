/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.repository;

import com.funesoft.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author faust
 */
@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer>{
    
}
