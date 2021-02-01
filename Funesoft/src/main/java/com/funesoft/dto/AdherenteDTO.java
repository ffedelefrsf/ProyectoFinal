/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.dto;

import com.sun.istack.Nullable;
import java.util.Date;
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
public class AdherenteDTO {
    
    private Integer id;
    
    @NotNull
    private int dni;
    
    @NotNull
    @Size(min = 1, max = 100)
    private String apellido;
    
    @NotNull
    @Size(min = 1, max = 100)
    private String nombre;
    
    @NotNull
    @Size(min = 1, max = 100)
    private String direccion;
    
    @NotNull
    private String telefono;
    
    @NotNull
    @Size(min = 1, max = 100)
    private String email;
    
    @NotNull
    @Size(min = 1, max = 50)
    private String sexo;
    
    @NotNull
    private Date fechaNacimiento;
    
    @NotNull
    private float saldo;
    
    @NotNull
    private Integer idZona;
    
    @NotNull
    private Integer idSocio;
    
    @NotNull
    private Integer idLocalidad;
    
    @NotNull
    private Integer idObraSocial;
    
    private Integer idEstado;
    
    private Integer idUsuarioModifica;
    
    @NotNull
    private Integer idEnfermedad;
    
    @Nullable
    private Date fechaCobertura;
    
}
