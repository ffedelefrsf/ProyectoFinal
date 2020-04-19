package com.funesoft.dto;

import com.funesoft.model.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ZonaDTO{

    private Integer id;
    private Integer nroZona;
    private String nombre;
    private Usuario usuarioModifica;

    public ZonaDTO(Integer idZona, Integer nroZona, String nombre) {
        this.id = idZona;
        this.nroZona = nroZona;
        this.nombre = nombre;
    }
}
