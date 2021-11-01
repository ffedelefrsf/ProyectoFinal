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

import com.funesoft.dto.RangoTarifaDTO;
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
@Table(name = "RANGOS_TARIFAS")
public class RangoTarifa implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "EDAD_DESDE")
    private int edadDesde;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "EDAD_HASTA")
    private int edadHasta;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALOR")
    private float valor;
    
    @JoinColumn(name = "ID_TARIFA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Tarifa tarifa;
    
    @JoinColumn(name = "ID_USUARIO_MODIFICA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Usuario usuarioModifica;

    public RangoTarifa(RangoTarifaDTO dto, Tarifa tarifa, Usuario user) {
        System.out.println("1");
        this.edadDesde = dto.getEdadDesde();
        System.out.println("2");
        this.edadHasta = dto.getEdadHasta();
        System.out.println("3");
        this.valor = dto.getValor().floatValue();
        System.out.println("4");
        this.tarifa = tarifa;
        System.out.println("5");
        this.usuarioModifica = user;
        System.out.println("6");
    }

    public RangoTarifa(RangoTarifa rango, Tarifa tarifa, Usuario user) {
        this.edadDesde = rango.getEdadDesde();
        this.edadHasta = rango.getEdadHasta();
        this.valor = rango.getValor();
        this.tarifa = tarifa;
        this.usuarioModifica = user;
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
        final RangoTarifa other = (RangoTarifa) obj;
        if (this.edadDesde != other.edadDesde) {
            return false;
        }
        if (this.edadHasta != other.edadHasta) {
            return false;
        }
        if (Float.floatToIntBits(this.valor) != Float.floatToIntBits(other.valor)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.tarifa, other.tarifa)) {
            return false;
        }
        if (!Objects.equals(this.usuarioModifica, other.usuarioModifica)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RangoTarifa{" + "id=" + id + ", edadDesde=" + edadDesde + ", edadHasta=" + edadHasta + ", valor=" + valor + ", tarifa=" + tarifa.toString() + ", usuarioModifica=" + (usuarioModifica != null ? usuarioModifica.toString() : "null") + '}';
    }

}
