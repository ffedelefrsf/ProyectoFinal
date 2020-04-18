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
@Table(name = "HISTORIAL_ESTADO_SOCIOS")
public class HistorialEstadoSocio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_ALTA")
    @Temporal(TemporalType.DATE)
    private Date fechaAlta;

    @JoinColumn(name = "ID_SOCIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Socio socio;
    
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Estado estado;
    
    @JoinColumn(name = "ID_MOTIVO_BAJA", referencedColumnName = "ID")
    @ManyToOne(optional = true)
    private MotivoBaja motivoBaja;
    
    @JoinColumn(name = "ID_USUARIO_MODIFICA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Usuario usuarioModifica;
    

    public HistorialEstadoSocio(Date fechaAlta, Socio socio, Estado estado, MotivoBaja motivoBaja, Usuario usuarioModifica) {
        this.fechaAlta = fechaAlta;
        this.socio = socio;
        this.estado = estado;
        this.motivoBaja = motivoBaja;
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
        final HistorialEstadoSocio other = (HistorialEstadoSocio) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.fechaAlta, other.fechaAlta)) {
            return false;
        }
        if (!Objects.equals(this.socio, other.socio)) {
            return false;
        }
        if (!Objects.equals(this.estado, other.estado)) {
            return false;
        }
        if (!Objects.equals(this.motivoBaja, other.motivoBaja)) {
            return false;
        }
        if (!Objects.equals(this.usuarioModifica, other.usuarioModifica)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "HistorialEstadoSocio{" + "id=" + id + ", fechaAlta=" + fechaAlta + ", socio=" + socio.toString() + ", estado=" + estado.toString() + ", motivoBaja=" + motivoBaja == null ? null : motivoBaja.toString() + ", usuario=" + usuarioModifica.toString() + '}';
    }
    
}
