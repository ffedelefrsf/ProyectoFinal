package com.funesoft.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ZonaDTO {

    private Integer idZona;
    private Integer nroZona;
    private String nombre;

    public ZonaDTO(Integer idZona, Integer nroZona, String nombre) {
        this.idZona = idZona;
        this.nroZona = nroZona;
        this.nombre = nombre;
    }
}
