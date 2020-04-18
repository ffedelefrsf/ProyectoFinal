/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@Table(name = "PAGOS")
public class Pago implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALOR")
    private float valor;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_ALTA")
    @Temporal(TemporalType.DATE)
    private Date fechaAlta;
    
    @JoinColumn(name = "ID_SOCIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Socio socio;
    
    @JoinColumn(name = "ID_COBRADOR", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Cobrador cobrador;
    
    @JoinColumn(name = "ID_COMPROBANTE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Comprobante comprobante;
    
    @JoinColumn(name = "ID_USUARIO_MODIFICA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Usuario usuarioModifica;

    
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
        final Pago other = (Pago) obj;
        if (Float.floatToIntBits(this.valor) != Float.floatToIntBits(other.valor)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.fechaAlta, other.fechaAlta)) {
            return false;
        }
        if (!Objects.equals(this.socio, other.socio)) {
            return false;
        }
        if (!Objects.equals(this.cobrador, other.cobrador)) {
            return false;
        }
        if (!Objects.equals(this.comprobante, other.comprobante)) {
            return false;
        }
        if (!Objects.equals(this.usuarioModifica, other.usuarioModifica)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pago{" + "id=" + id + ", valor=" + valor + ", fechaAlta=" + fechaAlta + ", socio=" + socio.toString() + ", cobrador=" + cobrador.toString() + ", comprobante=" + comprobante.toString() + ", usuarioModifica=" + usuarioModifica.toString() + '}';
    }

}
