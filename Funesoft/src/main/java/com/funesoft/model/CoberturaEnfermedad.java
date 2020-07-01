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
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author faust
 */
@Entity
@Table (name = "COBERTURA_ENFERMEDADES")
public class CoberturaEnfermedad implements Serializable{
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "ID")
    private Integer id;
    
    @OneToOne
    @JoinColumn (name = "ID_ENFERMEDAD", referencedColumnName = "ID", nullable = false)
    private Enfermedad enfermedad;
    
    @Column (name = "INDICADOR", nullable = false)
    private Float indicador;

    public CoberturaEnfermedad() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Enfermedad getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(Enfermedad enfermedad) {
        this.enfermedad = enfermedad;
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
        final CoberturaEnfermedad other = (CoberturaEnfermedad) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.enfermedad, other.enfermedad)) {
            return false;
        }
        if (!Objects.equals(this.indicador, other.indicador)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CoberturaEnfermedad{" + "id=" + id + ", enfermedad=" + enfermedad + ", indicador=" + indicador + '}';
    }
    
}
