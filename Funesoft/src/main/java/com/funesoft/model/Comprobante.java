/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.model;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "COMPROBANTES")
public class Comprobante implements Serializable {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "NRO_COMPROBANTE")
    private BigInteger nroComprobante;

    @Basic(optional = false)
    @NotNull
    @Column(name = "IMPORTE_TOTAL")
    private Double importeTotal;
    
    @JoinColumn(name = "ID_PARAMETRO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private ParametroEmpresa parametroEmpresa;
    
    @JoinColumn(name = "ID_USUARIO_MODIFICA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Usuario usuarioModifica;

    public Comprobante(BigInteger nroComprobante, Double importeTotal, ParametroEmpresa parametroEmpresa, Usuario usuarioModifica) {
        this.nroComprobante = nroComprobante;
        this.importeTotal = importeTotal;
        this.parametroEmpresa = parametroEmpresa;
        this.usuarioModifica = usuarioModifica;
    }

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
        final Comprobante other = (Comprobante) obj;
        if (this.nroComprobante != other.nroComprobante) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.parametroEmpresa, other.parametroEmpresa)) {
            return false;
        }
        if (!Objects.equals(this.usuarioModifica, other.usuarioModifica)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Comprobante{" + "id=" + id + ", nroComprobante=" + nroComprobante + ", parametroEmpresa=" + parametroEmpresa.toString() + ", usuario=" + usuarioModifica.toString() + '}';
    }
    
}
