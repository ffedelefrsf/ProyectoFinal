/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.model;

import com.funesoft.dto.SocioDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Getter
@Setter
@NoArgsConstructor
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

    public Socio(SocioDTO socioDTO){
        this.dni = socioDTO.getDni();
        this.apellido = socioDTO.getApellido();
        this.nombre = socioDTO.getNombre();
        this.direccion = socioDTO.getDireccion();
        this.telefono = socioDTO.getTelefono();
        this.email = socioDTO.getEmail();
        this.sexo = socioDTO.getSexo();
        this.fechaNacimiento = socioDTO.getFechaNacimiento();
        this.fechaCobertura = socioDTO.getFechaCobertura();
        this.usuarioAlta = socioDTO.getUsuarioAlta();
        this.saldo = socioDTO.getSaldo();
        this.idTarifa = socioDTO.getIdTarifa();
        this.idZona = socioDTO.getIdZona();
        this.idLocalidad = socioDTO.getIdLocalidad();
        this.idObraSocial = socioDTO.getIdObraSocial();
    }

}
