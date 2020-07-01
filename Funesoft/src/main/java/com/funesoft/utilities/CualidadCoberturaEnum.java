/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.utilities;

/**
 *
 * @author faust
 */
public enum CualidadCoberturaEnum {
    
    ENFERMEDAD("ENFERMEDAD"),
    EDAD("EDAD");
    
    private final String descripcion;

    private CualidadCoberturaEnum(final String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return this.name();
    }

    
}
