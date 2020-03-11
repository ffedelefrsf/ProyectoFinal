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
    @Column (name = "id_socio")
    private Integer idSocio;
    
    @Column (name = "dni")
    private Integer dni;
    
    @Column (name = "apellido")
    private String apellido;
    
    @Column (name = "nombre")
    private String nombre;
    
    @Column (name = "direccion")
    private String direccion;
    
    @Column (name = "telefono")
    private String telefono;
    
    @Column (name = "email")
    private String email;
    
    @Column (name = "sexo")
    private String sexo;
    
    @Column (name = "fecha_nacimiento")
    @Temporal (TemporalType.DATE)
    private Date fechaNacimiento;
    
    @Column (name = "usuario_alta")
    private Integer usuarioAlta;
    
    @Column (name = "saldo")
    private Double saldo;
    
    @Column (name = "id_tarifa")
    private Integer idTarifa;
    
    @Column (name = "id_zona")
    private Integer idZona;
    
    @Column (name = "id_localidad")
    private Integer idLocalidad;
    
    @Column (name = "id_obra_social")
    private Integer idObraSocial;

    public Socio() {
    }

    public Socio(Integer idSocio, Integer dni, String apellido, String nombre, String direccion, String telefono, String email, String sexo, Date fechaNacimiento, Integer usuarioAlta, Double saldo, Integer idTarifa, Integer idZona, Integer idLocalidad, Integer idObraSocial) {
        this.idSocio = idSocio;
        this.dni = dni;
        this.apellido = apellido;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.usuarioAlta = usuarioAlta;
        this.saldo = saldo;
        this.idTarifa = idTarifa;
        this.idZona = idZona;
        this.idLocalidad = idLocalidad;
        this.idObraSocial = idObraSocial;
    }

    public Socio(Integer dni, String apellido, String nombre, String direccion, String telefono, String email, String sexo, Date fechaNacimiento, Integer usuarioAlta, Double saldo, Integer idTarifa, Integer idZona, Integer idLocalidad, Integer idObraSocial) {
        this.dni = dni;
        this.apellido = apellido;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
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
