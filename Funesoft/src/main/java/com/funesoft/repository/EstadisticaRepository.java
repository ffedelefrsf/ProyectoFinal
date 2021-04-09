/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NamedNativeQuery;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Repository
public class EstadisticaRepository {

    @Autowired
    private EntityManager entityManager;

    public List<Object[]> findIngresoAnual() {
        return entityManager.createNativeQuery("SELECT YEAR(PA.FECHA), IFNULL(SUM(P.VALOR), 0) " +
                "FROM sql10328339.PAGOS_AUDIT PA " +
                "INNER JOIN sql10328339.PAGOS P ON PA.ID = P.ID " +
                "GROUP BY YEAR(PA.FECHA)" +
                "ORDER BY FECHA DESC LIMIT 2").getResultList();
    }

    public List<Object[]> findAIngresar() {
        return entityManager.createNativeQuery(
                "SELECT IFNULL(MONTHNAME(CA.FECHA), MONTHNAME(CURRENT_DATE())), IFNULL(SUM(C.IMPORTE_TOTAL), 0) " +
                        "FROM sql10328339.COMPROBANTES C " +
                        "LEFT JOIN sql10328339.PAGOS P ON C.ID = P.ID_COMPROBANTE " +
                        "INNER JOIN sql10328339.COMPROBANTES_AUDIT CA ON C.ID = CA.ID AND ca.METODO = 'INSERT' " +
                        "WHERE P.ID IS NULL AND MONTH(CA.FECHA) = MONTH(CURRENT_DATE()) AND YEAR(CA.FECHA) = YEAR(CURRENT_DATE()) "
        ).getResultList();
    }

    public List<Object[]> findSociosActivos() {
        return entityManager.createNativeQuery(
                "SELECT S.ID_ESTADO, COUNT(S.ID) " +
                        "FROM sql10328339.SOCIOS S " +
                        "GROUP BY S.ID_ESTADO "
        ).getResultList();
    }

    public List<Object[]> findNuevosSocios() {
        entityManager.createNativeQuery("").getFirstResult();
        return entityManager.createNativeQuery(
                "SELECT COUNT(S.ID), YEAR(CURRENT_DATE()) " +
                        "FROM sql10328339.SOCIOS S " +
                        "INNER JOIN sql10328339.SOCIOS_AUDIT SA ON SA.ID = S.ID AND SA.METODO = 'INSERT' " +
                        "WHERE S.ID_ESTADO = 1 AND YEAR(SA.FECHA) = YEAR(CURRENT_DATE()) "
        ).getResultList();
    }

}
