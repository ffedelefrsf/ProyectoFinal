/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.repository;

import com.funesoft.model.Localidad;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author faust
 */
@Repository
public interface LocalidadRepository extends JpaRepository<Localidad, Integer>{
    
    @Query("SELECT L.nombre FROM Localidad L WHERE L.provincia.id = :id")
    public List<String> findNombreByProvinciaId(Integer id);
    
}
