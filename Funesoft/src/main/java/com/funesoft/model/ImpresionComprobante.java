package com.funesoft.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "IMPRESION_COMPROBANTES")
public class ImpresionComprobante implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Column(name = "IMPORTE")
    private Double importe;

    @JoinColumn(name = "ID_COMPROBANTE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Comprobante comprobante;

    @JoinColumn(name = "ID_ADHERENTE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Adherente adherente;

    @JoinColumn(name = "ID_USUARIO_MODIFICA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Usuario usuarioModifica;

    public ImpresionComprobante(Double importe, Comprobante cbte, Adherente adh, Usuario usuario) {
        this.importe = importe;
        this.comprobante = cbte;
        this.adherente = adh;
        this.usuarioModifica = usuario;
    }

}
