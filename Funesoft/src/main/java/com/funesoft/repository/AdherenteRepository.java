/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.repository;

import com.funesoft.model.Adherente;
import com.funesoft.model.Socio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author faust
 */
@Repository
public interface AdherenteRepository extends JpaRepository<Adherente, Integer>{
    
    public List<Adherente> findBySocio(Socio socio);
    
}
