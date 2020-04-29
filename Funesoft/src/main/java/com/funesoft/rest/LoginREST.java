/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.rest;

import com.funesoft.dto.LoginDTO;
import com.funesoft.model.Usuario;
import com.funesoft.repository.UsuarioRepository;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author faust
 */
@RestController
public class LoginREST {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @PostMapping(path = "login")
    public ResponseEntity<?> doLogin(@RequestBody LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response){
        final String enteredUsername = loginDTO.getUsername();
        final String enteredPassword = loginDTO.getPassword();
        final UserDetails userDetails;
        try{
            userDetails = usuarioRepository.findByUsername(enteredUsername);
            if (enteredPassword.equals(userDetails.getPassword())){
                return new ResponseEntity<>(true, HttpStatus.OK);
            }else{
                request.getSession().invalidate();
                return new ResponseEntity<>(false, HttpStatus.OK);
            }
        }catch (Exception exception){
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }
    
    @GetMapping(path = "logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response){
        try{
            request.getSession().invalidate();
            return new ResponseEntity<>(true, HttpStatus.OK);
        }catch(Exception exception){
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }
    
    @GetMapping(path = "check")
    public ResponseEntity<?> checkCredentials(){
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
