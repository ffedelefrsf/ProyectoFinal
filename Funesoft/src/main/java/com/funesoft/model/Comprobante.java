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

    @Basic(optional = false)
    @NotNull
    @Column(name = "IMPRESO")
    private Boolean impreso;

    @JoinColumn(name = "ID_SOCIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Socio socio;
    
    @JoinColumn(name = "ID_USUARIO_MODIFICA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Usuario usuarioModifica;

    public Comprobante(BigInteger nroComprobante, Double importeTotal, Usuario usuarioModifica, Socio socio, Boolean impreso) {
        this.nroComprobante = nroComprobante;
        this.importeTotal = importeTotal;
        this.usuarioModifica = usuarioModifica;
        this.socio = socio;
        this.impreso = impreso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comprobante that = (Comprobante) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(nroComprobante, that.nroComprobante) &&
                Objects.equals(importeTotal, that.importeTotal) &&
                Objects.equals(socio, that.socio) &&
                Objects.equals(usuarioModifica, that.usuarioModifica);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nroComprobante, importeTotal, socio, usuarioModifica);
    }

    @Override
    public String toString() {
        return "Comprobante{" +
                "id=" + id +
                ", nroComprobante=" + nroComprobante +
                ", importeTotal=" + importeTotal +
                ", socio=" + socio +
                ", usuarioModifica=" + usuarioModifica +
                '}';
    }
}
