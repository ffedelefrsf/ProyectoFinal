/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.rest;

import com.funesoft.dto.LoginDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author faust
 */
@RestController
public class LoginREST {
    
    @PostMapping(path = "login")
    public ResponseEntity<?> doLogin(@RequestBody LoginDTO loginDTO){
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
