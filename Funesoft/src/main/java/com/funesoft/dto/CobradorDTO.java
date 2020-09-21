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

}
