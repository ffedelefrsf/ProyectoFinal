/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.repository;

import com.funesoft.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author faust
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    
    public Usuario findByUsername(String username);
    
}
