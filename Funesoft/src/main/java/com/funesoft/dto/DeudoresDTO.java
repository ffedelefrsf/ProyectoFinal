/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeudoresDTO {
    
    private Integer idSocio;
    private String nombre;
    private String apellido;
    private Double saldo;
    private Integer mesesDebe;
    private String fechaPrimerCbte;
    
}
