/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.dto.response.socio;

import com.funesoft.dto.response.error.ErrorResponseDTO;

/**
 *
 * @author faust
 */
public class SocioInsertResponseDTO extends ErrorResponseDTO{
    
    private Integer idSocio;

    public SocioInsertResponseDTO() {
    }

    public SocioInsertResponseDTO(Integer idSocio) {
        this.idSocio = idSocio;
    }

    public Integer getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(Integer idSocio) {
        this.idSocio = idSocio;
    }
    
    
    
}
