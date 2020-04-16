/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.model;

import com.funesoft.dto.LocalidadDTO;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author faust
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "LOCALIDADES")
public class Localidad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    
    @Column(name = "NRO_LOCALIDAD")
    private Integer nroLocalidad;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "NOMBRE")
    private String nombre;
    
    @Basic(optional = false)
    @Column(name = "CODIGO_POSTAL")
    private Integer codigoPostal;
    
    @JoinColumn(name = "ID_PROVINCIA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Provincia provincia;
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
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
        final Localidad other = (Localidad) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.nroLocalidad, other.nroLocalidad)) {
            return false;
        }
        if (!Objects.equals(this.codigoPostal, other.codigoPostal)) {
            return false;
        }
        if (!Objects.equals(this.provincia, other.provincia)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Localidad{" + "id=" + id + ", nroLocalidad=" + nroLocalidad + ", nombre=" + nombre + ", codigoPostal=" + codigoPostal + ", provincia=" + provincia + '}';
    }
    
    public Localidad (final LocalidadDTO localidadDTO){
        this.id = localidadDTO.getId();
        this.nroLocalidad = localidadDTO.getNroLocalidad();
        this.nombre = localidadDTO.getNombre();
        this.provincia = localidadDTO.getProvincia();
    }
}
