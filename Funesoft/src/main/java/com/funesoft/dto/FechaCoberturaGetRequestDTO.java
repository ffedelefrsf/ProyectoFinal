/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.dto;

import com.funesoft.model.Enfermedad;
import java.util.Date;
import javax.validation.constraints.NotNull;
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
public class FechaCoberturaGetRequestDTO {
    
    @NotNull
    private Date fechaNacimientoAsociado;
    
    @NotNull
    private Integer idEnfermedadAsociado;
    
}
