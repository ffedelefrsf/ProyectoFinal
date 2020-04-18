package com.funesoft.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "SOCIOS_BAJA")
public class SocioBaja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    @JoinColumn(name = "ID_SOCIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Socio socio;

    @JoinColumn(name = "ID_MOTIVO_BAJA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private MotivoBaja motivoBaja;

    public SocioBaja(Socio socio, MotivoBaja motivoBaja) {
        this.socio = socio;
        this.motivoBaja = motivoBaja;
    }
}
