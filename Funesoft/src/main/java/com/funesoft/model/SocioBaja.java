package com.funesoft.model;

import java.util.Objects;
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
    
    @JoinColumn(name = "ID_USUARIO_MODIFICA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Usuario usuarioModifica;

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SocioBaja other = (SocioBaja) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.socio, other.socio)) {
            return false;
        }
        if (!Objects.equals(this.motivoBaja, other.motivoBaja)) {
            return false;
        }
        if (!Objects.equals(this.usuarioModifica, other.usuarioModifica)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SocioBaja{" + "id=" + id + ", socio=" + socio.toString() + ", motivoBaja=" + motivoBaja.toString() + ", usuarioModifica=" + usuarioModifica.toString() + '}';
    }

    public SocioBaja(Socio socio, MotivoBaja motivoBaja, Usuario usuarioModifica) {
        this.socio = socio;
        this.motivoBaja = motivoBaja;
        this.usuarioModifica = usuarioModifica;
    }
}
