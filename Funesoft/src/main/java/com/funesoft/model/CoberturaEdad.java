/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author faust
 */
@Entity
@Table (name = "COBERTURA_EDADES")
public class CoberturaEdad implements Serializable{
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "ID")
    private Integer id;
    
    @Column (name = "EDAD_DESDE", nullable = false)
    private Short edadDesde;
    
    @Column (name = "EDAD_HASTA", nullable = false)
    private Short edadHasta;
    
    @Column (name = "INDICADOR", nullable = false)
    private Float indicador;

    public CoberturaEdad() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getEdadDesde() {
        return edadDesde;
    }

    public void setEdadDesde(Short edadDesde) {
        this.edadDesde = edadDesde;
    }

    public Short getEdadHasta() {
        return edadHasta;
    }

    public void setEdadHasta(Short edadHasta) {
        this.edadHasta = edadHasta;
    }

    public Float getIndicador() {
        return indicador;
    }

    public void setIndicador(Float indicador) {
        this.indicador = indicador;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CoberturaEdad other = (CoberturaEdad) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.edadDesde, other.edadDesde)) {
            return false;
        }
        if (!Objects.equals(this.edadHasta, other.edadHasta)) {
            return false;
        }
        if (!Objects.equals(this.indicador, other.indicador)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CoberturaEdad{" + "id=" + id + ", edadDesde=" + edadDesde + ", edadHasta=" + edadHasta + ", indicador=" + indicador + '}';
    }
    
}
