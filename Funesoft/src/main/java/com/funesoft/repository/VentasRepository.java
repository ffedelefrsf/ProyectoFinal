/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class VentasRepository {

    @Autowired
    private EntityManager entityManager;

    public List<Object[]> findVentasTotal() {
        return entityManager.createNativeQuery("SELECT V.ID, V.NRO_VENTA, V.DESCRIPCION, V.ID_SERVICIO, VA.FECHA " +
                "FROM VENTA V " +
                "INNER JOIN VENTA_AUDIT VA ON VA.ID = V.ID ").getResultList();
    }

}
