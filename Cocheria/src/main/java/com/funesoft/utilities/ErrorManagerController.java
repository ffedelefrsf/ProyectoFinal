/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.utilities;

import com.funesoft.dto.response.error.ErrorResponseDTO;
import java.util.List;
import org.springframework.stereotype.Controller;

/**
 *
 * @author faust
 */
@Controller
public class ErrorManagerController {
    
    public static final String  PARAMETROS_INVALIDOS = "Los parámetros ingresados son incorrectos.",
                                CLAVE_DUPLICADA = "Ya existe una entidad con esos datos.";
    
    public ErrorResponseDTO checkParameters(List<Object> parameters){
        for (Object parameter : parameters){
            
            if (parameter == null)
                return new ErrorResponseDTO(PARAMETROS_INVALIDOS);
            
            
            // Integer
            if (parameter instanceof Integer){
                final Integer entero = (Integer) parameter;
                if (entero < 0)
                    return new ErrorResponseDTO(PARAMETROS_INVALIDOS);
            }
            
            // String
            if (parameter instanceof String){
                final String string = (String) parameter;
                if (string.isEmpty())
                    return new ErrorResponseDTO(PARAMETROS_INVALIDOS);
            }
        }
        return null;
    }
    
    
}
