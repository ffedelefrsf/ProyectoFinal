/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.repository;

import com.funesoft.model.Comprobante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

/**
 *
 * @author faust
 */
@Repository
public interface ComprobanteRepository extends JpaRepository<Comprobante, Integer>{
    @Query(value = "SELECT * FROM COMPROBANTES ORDER BY 2 DESC LIMIT 1", nativeQuery = true)
    Optional<Comprobante> findUltimoComprobante();
}
