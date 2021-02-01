/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.repository;

import com.funesoft.model.Estado;
import com.funesoft.model.Socio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author faust
 */
@Repository
public interface SocioRepository extends JpaRepository<Socio, Integer>{

    @Query("SELECT s FROM Socio s WHERE s.id = :idSocio AND s.estado.id = 1")
    Optional<Socio> findByIdActivo(
            @Param("idSocio") Integer id
    );

    @Query("SELECT s FROM Socio s WHERE s.estado.id = 1")
    List<Socio> findAllActivo();
    
    @Query("SELECT dni FROM Socio ORDER BY dni DESC")
    List<String> findDniByOrderByDniDesc();
}
