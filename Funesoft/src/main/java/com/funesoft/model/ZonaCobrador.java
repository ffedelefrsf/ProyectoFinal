/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.model;

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
@Table(name = "ZONAS_COBRADORES")
public class ZonaCobrador implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    
    @JoinColumn(name = "ID_ZONA", referencedColumnName = "ID")
    @ManyToOne
    private Zona zona;
    
    @JoinColumn(name = "ID_COBRADOR", referencedColumnName = "ID")
    @ManyToOne
    private Cobrador cobrador;

    
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
        final ZonaCobrador other = (ZonaCobrador) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.zona, other.zona)) {
            return false;
        }
        if (!Objects.equals(this.cobrador, other.cobrador)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ZonasCobradores{" + "id=" + id + ", idZona=" + zona.toString() + ", idCobrador=" + cobrador.toString() + '}';
    }
    
}
