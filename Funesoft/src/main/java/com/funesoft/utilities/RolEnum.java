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
public enum RolEnum {
    
    ADMIN(1);
    
    private final Integer codigo;

    private RolEnum(final Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
