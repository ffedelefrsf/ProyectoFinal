/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author faust
 */
@Entity
@Table (name = "SOCIOS")
public class Socio implements Serializable{
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "ID")
    private Integer idSocio;
    
    @Column (name = "DNI")
    private Integer dni;
    
    @Column (name = "APELLIDO")
    private String apellido;
    
    @Column (name = "NOMBRE")
    private String nombre;
    
    @Column (name = "DIRECCION")
    private String direccion;
    
    @Column (name = "TELEFONO")
    private String telefono;
    
    @Column (name = "EMAIL")
    private String email;
    
    @Column (name = "SEXO")
    private String sexo;
    
    @Column (name = "FECHA_NACIMIENTO")
    @Temporal (TemporalType.DATE)
    private Date fechaNacimiento;
    
    @Column (name = "FECHA_COBERTURA")
    @Temporal (TemporalType.DATE)
    private Date fechaCobertura;
    
    @Column (name = "USUARIO_ALTA")
    private Integer usuarioAlta;
    
    @Column (name = "SALDO")
    private Double saldo;
    
    @Column (name = "ID_TARIFA")
    private Integer idTarifa;
    
    @Column (name = "ID_ZONA")
    private Integer idZona;
    
    @Column (name = "ID_LOCALIDAD")
    private Integer idLocalidad;
    
    @Column (name = "ID_OBRA_SOCIAL")
    private Integer idObraSocial;

    public Socio() {
    }

    public Socio(Integer idSocio, Integer dni, String apellido, String nombre, String direccion, String telefono, String email, String sexo, Date fechaNacimiento, Date fechaCobertura, Integer usuarioAlta, Double saldo, Integer idTarifa, Integer idZona, Integer idLocalidad, Integer idObraSocial) {
        this.idSocio = idSocio;
        this.dni = dni;
        this.apellido = apellido;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaCobertura = fechaCobertura;
        this.usuarioAlta = usuarioAlta;
        this.saldo = saldo;
        this.idTarifa = idTarifa;
        this.idZona = idZona;
        this.idLocalidad = idLocalidad;
        this.idObraSocial = idObraSocial;
    }

    public Socio(Integer dni, String apellido, String nombre, String direccion, String telefono, String email, String sexo, Date fechaNacimiento, Date fechaCobertura, Integer usuarioAlta, Double saldo, Integer idTarifa, Integer idZona, Integer idLocalidad, Integer idObraSocial) {
        this.dni = dni;
        this.apellido = apellido;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaCobertura = fechaCobertura;
        this.usuarioAlta = usuarioAlta;
        this.saldo = saldo;
        this.idTarifa = idTarifa;
        this.idZona = idZona;
        this.idLocalidad = idLocalidad;
        this.idObraSocial = idObraSocial;
    }

    public Integer getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(Integer idSocio) {
        this.idSocio = idSocio;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Date getFechaCobertura() {
        return fechaCobertura;
    }

    public void setFechaCobertura(Date fechaCobertura) {
        this.fechaCobertura = fechaCobertura;
    }

    public Integer getUsuarioAlta() {
        return usuarioAlta;
    }

    public void setUsuarioAlta(Integer usuarioAlta) {
        this.usuarioAlta = usuarioAlta;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Integer getIdTarifa() {
        return idTarifa;
    }

    public void setIdTarifa(Integer idTarifa) {
        this.idTarifa = idTarifa;
    }

    public Integer getIdZona() {
        return idZona;
    }

    public void setIdZona(Integer idZona) {
        this.idZona = idZona;
    }

    public Integer getIdLocalidad() {
        return idLocalidad;
    }

    public void setIdLocalidad(Integer idLocalidad) {
        this.idLocalidad = idLocalidad;
    }

    public Integer getIdObraSocial() {
        return idObraSocial;
    }

    public void setIdObraSocial(Integer idObraSocial) {
        this.idObraSocial = idObraSocial;
    }

    
}
