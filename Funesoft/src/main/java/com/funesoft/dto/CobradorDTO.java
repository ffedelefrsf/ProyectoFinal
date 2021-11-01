package com.funesoft.dto;

import com.funesoft.model.Localidad;
import com.funesoft.model.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CobradorDTO {

    private Integer id;
    private Integer dni;
    private String apellido;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    private String sexo;
    private Date fechaNacimiento;
    private Date fechaAlta;
    private Date fechaBaja;
    private Integer idLocalidad;

    public CobradorDTO(Integer id, Integer dni, String apellido, String nombre, String direccion, String telefono, String email, String sexo, Date fechaNacimiento, Date fechaAlta, Date fechaBaja, Integer idLocalidad) {
        this.id = id;
        this.dni = dni;
        this.apellido = apellido;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
        this.idLocalidad = idLocalidad;
    }
    
}
