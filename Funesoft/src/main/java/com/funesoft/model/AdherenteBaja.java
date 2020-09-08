package com.funesoft.model;

import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ADHERENTES_BAJA")
public class AdherenteBaja implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    
    @JoinColumn(name = "ID_ADHERENTE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Adherente adherente;

    @JoinColumn(name = "ID_MOTIVO_BAJA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private MotivoBaja motivoBaja;
    
    @JoinColumn(name = "ID_USUARIO_MODIFICA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Usuario usuarioModifica;

    public AdherenteBaja(Adherente adherente, MotivoBaja motivoBaja, Usuario usuarioModifica) {
        this.adherente = adherente;
        this.motivoBaja = motivoBaja;
        this.usuarioModifica = usuarioModifica;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final AdherenteBaja other = (AdherenteBaja) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.adherente, other.adherente)) {
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
        return "AdherenteBaja{" + "id=" + id + ", adherente=" + adherente.toString() + ", motivoBaja=" + motivoBaja.toString() + ", usuarioModifica=" + usuarioModifica.toString() + '}';
    }
    
}
