package com.funesoft.utilities;

public enum EstadoEnum {

    ALTA(1),
    BAJA(2);
    
    private final Integer codigo;

    EstadoEnum(final Integer codigo) {
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
