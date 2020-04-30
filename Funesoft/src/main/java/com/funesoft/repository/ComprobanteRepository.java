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

/**
 *
 * @author faust
 */
@Repository
public interface ComprobanteRepository extends JpaRepository<Comprobante, Integer>{
    @Query("SELECT MAX(c.nroComprobante) FROM Comprobante c ")
    BigInteger ultimoNroComprobante();
}
