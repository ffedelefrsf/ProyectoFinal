/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.funesoft.dto.SocioDTO;
import com.funesoft.utilities.StringAttributeConverter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
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
    @Convert(converter = StringAttributeConverter.class)
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
    
    @JoinColumn(name = "ID_USUARIO_MODIFICA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Usuario usuarioModifica;

    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Estado estado;
    
    @ManyToOne
    @JoinColumn (name = "ID_ENFERMEDAD", referencedColumnName = "ID", nullable = false)
    private Enfermedad enfermedad;
    
    @Transient
    @JsonIgnore
    private Short edad;
    

    public Socio(SocioDTO socioDTO){
        this.dni = socioDTO.getDni();
        this.apellido = socioDTO.getApellido();
        this.nombre = socioDTO.getNombre();
        this.direccion = socioDTO.getDireccion();
        this.telefono = socioDTO.getTelefono();
        this.email = socioDTO.getEmail();
        this.sexo = socioDTO.getSexo();
        this.fechaNacimiento = socioDTO.getFechaNacimiento();
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
        if(socioDTO.getIdEnfermedad() != null){
            final Enfermedad enfermedad = new Enfermedad();
            enfermedad.setId(socioDTO.getIdEnfermedad());
            this.enfermedad = enfermedad;
        }
    }
    
    public short getEdad(){
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.fechaNacimiento);
        final Integer year = calendar.get(Calendar.YEAR), month = calendar.get(Calendar.MONTH)+1, day = calendar.get(Calendar.DAY_OF_MONTH);
        final LocalDate fechaNacimientoAux = LocalDate.of (year, month, day);
        final LocalDate fechaActual = LocalDate.now();
        return (short) ChronoUnit.YEARS.between(fechaNacimientoAux, fechaActual);
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Socio other = (Socio) obj;
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
        if (!Objects.equals(this.dni, other.dni)) {
            return false;
        }
        if (!Objects.equals(this.fechaNacimiento, other.fechaNacimiento)) {
            return false;
        }
        if (!Objects.equals(this.fechaCobertura, other.fechaCobertura)) {
            return false;
        }
        if (!Objects.equals(this.saldo, other.saldo)) {
            return false;
        }
        if (!Objects.equals(this.tarifa, other.tarifa)) {
            return false;
        }
        if (!Objects.equals(this.zona, other.zona)) {
            return false;
        }
        if (!Objects.equals(this.localidad, other.localidad)) {
            return false;
        }
        if (!Objects.equals(this.obraSocial, other.obraSocial)) {
            return false;
        }
        if (!Objects.equals(this.usuarioModifica, other.usuarioModifica)) {
            return false;
        }
        if (!Objects.equals(this.estado, other.estado)) {
            return false;
        }
        if (!Objects.equals(this.enfermedad, other.enfermedad)) {
            return false;
        }
        if (!Objects.equals(this.edad, other.edad)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Socio{" + "id=" + id + ", dni=" + dni + ", apellido=" + apellido + ", nombre=" + nombre + ", direccion=" + direccion + ", telefono=" + telefono + ", email=" + email + ", sexo=" + sexo + ", fechaNacimiento=" + fechaNacimiento + ", fechaCobertura=" + fechaCobertura + ", saldo=" + saldo + ", tarifa=" + tarifa + ", zona=" + zona + ", localidad=" + localidad + ", obraSocial=" + obraSocial + ", usuarioModifica=" + usuarioModifica + ", estado=" + estado + ", enfermedad=" + enfermedad + ", edad=" + edad + '}';
    }
    
}
