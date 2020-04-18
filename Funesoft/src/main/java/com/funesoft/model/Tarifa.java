/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.model;

import com.funesoft.dto.TarifaDTO;
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
@Table(name = "TARIFAS")
public class Tarifa implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    
    @Column(name = "NRO_TARIFA")
    private Integer nroTarifa;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALOR")
    private Float valor;
    
    @JoinColumn(name = "ID_PLAN", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Plan plan;
    
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
        final Tarifa other = (Tarifa) obj;
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.nroTarifa, other.nroTarifa)) {
            return false;
        }
        if (!Objects.equals(this.valor, other.valor)) {
            return false;
        }
        if (!Objects.equals(this.plan, other.plan)) {
            return false;
        }
        if (!Objects.equals(this.usuarioModifica, other.usuarioModifica)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Tarifa{" + "id=" + id + ", nroTarifa=" + nroTarifa + ", descripcion=" + descripcion + ", valor=" + valor + ", plan=" + plan.toString() + ", usuarioModifica=" + usuarioModifica.toString() + '}';
    }
    
    public Tarifa(TarifaDTO tarifaDTO){
        this.id = tarifaDTO.getId();
        this.descripcion = tarifaDTO.getDescripcion();
        this.nroTarifa = tarifaDTO.getNroTarifa();
        this.valor = tarifaDTO.getValor();
        this.plan = tarifaDTO.getPlan();
        this.usuarioModifica = tarifaDTO.getUsuarioModifica();
    }
    
}
