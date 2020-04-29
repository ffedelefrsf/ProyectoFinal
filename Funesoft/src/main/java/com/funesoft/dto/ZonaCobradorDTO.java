package com.funesoft.dto;

import com.funesoft.model.Usuario;

public class ZonaCobradorDTO {

    Integer id;
    Integer idZona;
    Integer idCobrador;
    Usuario usuarioModifica;

    public ZonaCobradorDTO() {
    }

    public ZonaCobradorDTO(Integer idZona, Integer idCobrador, Usuario usuarioModifica) {
        this.idZona = idZona;
        this.idCobrador = idCobrador;
        this.usuarioModifica = usuarioModifica;
    }

    public ZonaCobradorDTO(Integer id, Integer idZona, Integer idCobrador, Usuario usuarioModifica) {
        this.id = id;
        this.idZona = idZona;
        this.idCobrador = idCobrador;
        this.usuarioModifica = usuarioModifica;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdZona() {
        return idZona;
    }

    public void setIdZona(Integer idZona) {
        this.idZona = idZona;
    }

    public Integer getIdCobrador() {
        return idCobrador;
    }

    public void setIdCobrador(Integer idCobrador) {
        this.idCobrador = idCobrador;
    }

    public Usuario getUsuarioModifica() {
        return usuarioModifica;
    }

    public void setUsuarioModifica(Usuario usuarioModifica) {
        this.usuarioModifica = usuarioModifica;
    }
}
