/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.dto;

import com.funesoft.model.Usuario;
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
public class ServicioDTO {
    
    private Integer id;
    private Integer nroServicio;
    private String descripcion;
    private Float valor;
    private Usuario usuarioModifica;
    
}
