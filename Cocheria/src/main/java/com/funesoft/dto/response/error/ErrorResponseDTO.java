/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.dto.response.error;

/**
 *
 * @author faust
 */
public class ErrorResponseDTO {
    
    private String errorDescription;

    public ErrorResponseDTO() {
    }

    public ErrorResponseDTO(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
    
}
