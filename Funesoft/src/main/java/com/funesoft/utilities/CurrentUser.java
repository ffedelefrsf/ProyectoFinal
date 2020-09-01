/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.utilities;

import com.funesoft.model.Usuario;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.context.annotation.RequestScope;

/**
 *
 * @author faust
 */
@Getter
@Setter
@RequestScope
public class CurrentUser {
    
    private static CurrentUser instance;
    
    private Usuario usuario;

    private CurrentUser() {
    }
    
    private CurrentUser(Usuario usuario){
        this.usuario = usuario;
    }
    
    public static CurrentUser newInstance(Usuario usuario){
        if (instance == null){
            instance = new CurrentUser(usuario);
        }
        return instance;
    }
    
    public static Usuario getInstance(){
        if (instance == null){
            instance = new CurrentUser();
        }
        return instance.getUsuario();
    }
    
}
