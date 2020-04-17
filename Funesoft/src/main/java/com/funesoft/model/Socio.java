/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.model;

import com.funesoft.dto.SocioDTO;
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
@Table(name = "SOCIOS")
public class Socio implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "DNI")
    private Integer dni;
    
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
    @Column(name = "USUARIO_ALTA")
    private Integer usuarioAlta;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "SALDO")
    private Double saldo;
    
    @JoinColumn(name = "ID_TARIFA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Tarifa tarifa;

    @JoinColumn(name = "ID_ZONA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Zona zona;

    @JoinColumn(name = "ID_LOCALIDAD", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Localidad localidad;

    @JoinColumn(name = "ID_OBRA_SOCIAL", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private ObraSocial obraSocial;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Socio{" + "id=" + id + ", dni=" + dni + ", apellido=" + apellido + ", nombre=" + nombre + ", direccion=" + direccion + ", telefono=" + telefono + ", email=" + email + ", sexo=" + sexo + ", fechaNacimiento=" + fechaNacimiento + ", fechaCobertura=" + fechaCobertura + ", usuarioAlta=" + usuarioAlta + ", saldo=" + saldo + ", idTarifa=" + tarifa + ", idZona=" + zona + ", idLocalidad=" + localidad + ", idObraSocial=" + obraSocial + '}';
    }
    
    public Socio(SocioDTO socioDTO){
        this.dni = socioDTO.getDni();
        this.apellido = socioDTO.getApellido();
        this.nombre = socioDTO.getNombre();
        this.direccion = socioDTO.getDireccion();
        this.telefono = socioDTO.getTelefono();
        this.email = socioDTO.getEmail();
        this.sexo = socioDTO.getSexo();
        this.fechaNacimiento = socioDTO.getFechaNacimiento();
        this.usuarioAlta = socioDTO.getUsuarioAlta();
        this.saldo = socioDTO.getSaldo();
        if(socioDTO.getIdTarifa() != null){
            final Tarifa tarifa = new Tarifa();
            tarifa.setId(socioDTO.getIdTarifa());
            this.tarifa = tarifa;
        }
        if(socioDTO.getIdZona() != null){
            final Zona zona = new Zona();
            zona.setId(socioDTO.getIdZona());
            this.zona = zona;
        }
        if(socioDTO.getIdLocalidad() != null){
            final Localidad localidad = new Localidad();
            localidad.setId(socioDTO.getIdLocalidad());
            this.localidad = localidad;
        }
        if(socioDTO.getIdObraSocial() != null){
            final ObraSocial obraSocial = new ObraSocial();
            obraSocial.setId(socioDTO.getIdObraSocial());
            this.obraSocial = obraSocial;
        }
    }
    
}
