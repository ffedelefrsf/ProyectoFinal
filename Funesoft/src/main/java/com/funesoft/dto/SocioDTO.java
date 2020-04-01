package com.funesoft.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author faust
 */

@Getter
@Setter
@NoArgsConstructor
public class SocioDTO {
    @NotNull
    @Max(99999999)
    @Min(999999)
    private Integer dni;
    @NotNull
    private String apellido;
    @NotNull
    private String nombre;
    @NotNull
    private String direccion;
    @NotNull
    @Size(min = 5, max = 10)
    private String telefono;
    private String email;
    @NotNull
    private String sexo;
    @NotNull
    @DateTimeFormat(pattern="dd-MM-yyyy")
    private Date fechaNacimiento;
    @DateTimeFormat(pattern="dd-MM-yyyy")
    @JsonIgnore
    private Date fechaCobertura;
    @NotNull
    private Integer usuarioAlta;
    @NotNull
    private Double saldo;
    @NotNull
    private Integer idTarifa;
    @NotNull
    private Integer idZona;
    @NotNull
    private Integer idLocalidad;
    @NotNull
    private Integer idObraSocial;

    public SocioDTO(@Size(min = 7, max = 8) Integer dni, String apellido, String nombre, String direccion, @Size(min = 5, max = 10) String telefono, String email, String sexo, Date fechaNacimiento, Date fechaCobertura, Integer usuarioAlta, Double saldo, Integer idTarifa, Integer idZona, Integer idLocalidad, Integer idObraSocial) {
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

}
