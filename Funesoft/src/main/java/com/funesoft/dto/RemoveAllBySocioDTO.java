/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.dto;

import com.funesoft.model.MotivoBaja;
import com.funesoft.model.Socio;
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
public class RemoveAllBySocioDTO {
    
    private Socio socio;
    private MotivoBaja motivoBaja;
    
}
