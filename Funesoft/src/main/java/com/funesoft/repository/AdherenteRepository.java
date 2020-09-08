/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.repository;

import com.funesoft.model.Adherente;
import com.funesoft.model.Socio;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author faust
 */
@Repository
public interface AdherenteRepository extends JpaRepository<Adherente, Integer>{
    
    List<Adherente> findBySocioOrderByFechaNacimientoAsc(Socio socio);

    Optional<Adherente> findByDni(Integer dni);
    
    @Query("SELECT A FROM Adherente A WHERE A.socio.dni LIKE CONCAT(:dniSocio, '%')")
    public List<Adherente> findByDniSocioLike(Integer dniSocio);
    
    public List<Adherente> findAllByOrderBySocioDniDesc();

}
