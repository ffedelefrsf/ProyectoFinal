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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@Table(name = "ADHERENTES")
public class Adherente implements Serializable {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "DNI")
    private int dni;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "APELLIDO")
    private String apellido;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "NOMBRE")
    private String nombre;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "DIRECCION")
    private String direccion;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "TELEFONO")
    private String telefono;
    
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "EMAIL")
    private String email;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "SEXO")
    private String sexo;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_NACIMIENTO")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_COBERTURA")
    @Temporal(TemporalType.DATE)
    private Date fechaCobertura;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "SALDO")
    private float saldo;
    
    @JoinColumn(name = "ID_ZONA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Zona zona;
    
    @JoinColumn(name = "ID_SOCIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Socio socio;
    
    @JoinColumn(name = "ID_LOCALIDAD", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Localidad localidad;
    
    @JoinColumn(name = "ID_OBRA_SOCIAL", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private ObraSocial obraSocial;
    
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID")
    @OneToOne(optional = false)
    private Estado estado;
    
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
        final Adherente other = (Adherente) obj;
        if (this.dni != other.dni) {
            return false;
        }
        if (Float.floatToIntBits(this.saldo) != Float.floatToIntBits(other.saldo)) {
            return false;
        }
        if (!Objects.equals(this.apellido, other.apellido)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.direccion, other.direccion)) {
            return false;
        }
        if (!Objects.equals(this.telefono, other.telefono)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.sexo, other.sexo)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.fechaNacimiento, other.fechaNacimiento)) {
            return false;
        }
        if (!Objects.equals(this.fechaCobertura, other.fechaCobertura)) {
            return false;
        }
        if (!Objects.equals(this.zona, other.zona)) {
            return false;
        }
        if (!Objects.equals(this.socio, other.socio)) {
            return false;
        }
        if (!Objects.equals(this.localidad, other.localidad)) {
            return false;
        }
        if (!Objects.equals(this.obraSocial, other.obraSocial)) {
            return false;
        }
        if (!Objects.equals(this.estado, other.estado)) {
            return false;
        }
        if (!Objects.equals(this.usuarioModifica, other.usuarioModifica)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Adherente{" + "id=" + id + ", dni=" + dni + ", apellido=" + apellido + ", nombre=" + nombre + ", direccion=" + direccion + ", telefono=" + telefono + ", email=" + email + ", sexo=" + sexo + ", fechaNacimiento=" + fechaNacimiento + ", fechaCobertura=" + fechaCobertura + ", saldo=" + saldo + ", zona=" + zona.toString() + ", socio=" + socio.toString() + ", localidad=" + localidad.toString() + ", obraSocial=" + obraSocial.toString() + ", estado=" + estado.toString() + ", usuario=" + usuarioModifica.toString() + '}';
    }
    
}
