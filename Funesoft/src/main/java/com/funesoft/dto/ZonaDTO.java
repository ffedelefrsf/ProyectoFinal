package com.funesoft.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ZonaDTO extends RequestDTO{

    private Integer id;
    private Integer nroZona;
    private String nombre;

    public ZonaDTO(Integer idZona, Integer nroZona, String nombre) {
        this.id = idZona;
        this.nroZona = nroZona;
        this.nombre = nombre;
    }
}
