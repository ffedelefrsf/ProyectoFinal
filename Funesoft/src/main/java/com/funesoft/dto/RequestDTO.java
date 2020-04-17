/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.dto;

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
public class RequestDTO{
    
    private Integer pagina;
    private Integer cantidad;
    private String sortField;
    private Boolean sortWay; // 1 ascending - 0 descending
    
}
