/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.dto;

import com.funesoft.model.Plan;
import com.funesoft.model.RangoTarifa;
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
public class TarifaDTO{
    
    private Integer id;
    private Integer nroTarifa;
    private String descripcion;
    private Float valor;
    private RangoTarifa rangoTarifa;
    private Plan plan;
    private Usuario usuarioModifica;
    
}
