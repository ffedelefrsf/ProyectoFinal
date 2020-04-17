package com.funesoft.utilities;

public enum EstadoEnum {

    ALTA("Alta socio", 1),
    BAJA("Baja socio", 2);

    private String descripcion;
    private Integer codigo;

    EstadoEnum(final String descripcion, final Integer codigo) {
        this.descripcion = descripcion;
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return this.descripcion;
    }

}
