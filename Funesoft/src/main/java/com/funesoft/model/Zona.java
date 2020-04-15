package com.funesoft.model;

import com.funesoft.dto.ZonaDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ZONAS")
public class Zona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "ID", length = 8)
    private Integer idZona;

    @Column (name = "NRO_ZONA", length = 8)
    private Integer nroZona;

    @Column (name = "NOMBRE", length = 50)
    private String nombre;

    public Zona(Integer idZona, Integer nroZona, String nombre) {
        this.idZona = idZona;
        this.nroZona = nroZona;
        this.nombre = nombre;
    }

    public Zona(ZonaDTO zonaDTO) {
        this.idZona = zonaDTO.getIdZona();
        this.nroZona = zonaDTO.getNroZona();
        this.nombre = zonaDTO.getNombre();
    }
}
