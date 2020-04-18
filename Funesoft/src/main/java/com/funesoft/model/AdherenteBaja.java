package com.funesoft.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ADHERENTES_BAJA")
public class AdherenteBaja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    @JoinColumn(name = "ID_MOTIVO_BAJA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private MotivoBaja motivoBaja;

}
