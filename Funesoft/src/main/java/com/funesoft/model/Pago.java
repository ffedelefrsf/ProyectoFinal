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
import javax.validation.constraints.NotNull;

import com.funesoft.dto.PagoDTO;
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

    public Pago(PagoDTO dto) {

        this.valor = dto.getValor().floatValue();

        if(dto.getIdSocio() != null){
            final Socio socio = new Socio();
            socio.setId(dto.getIdSocio());
            this.socio = socio;
        }

        if(dto.getIdCobrador() != null){
            final Cobrador cobrador = new Cobrador();
            cobrador.setId(dto.getIdCobrador());
            this.cobrador = cobrador;
        }

        if(dto.getIdComprobante() != null){
            final Comprobante cbte = new Comprobante();
            cbte.setId(dto.getIdComprobante());
            this.comprobante = cbte;
        }
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
        final Pago other = (Pago) obj;
        if (Float.floatToIntBits(this.valor) != Float.floatToIntBits(other.valor)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
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

}
