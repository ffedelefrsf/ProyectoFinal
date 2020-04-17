package com.funesoft.dto;

import com.funesoft.utilities.ExcepcionParser;

import java.util.ArrayList;
import java.util.List;

public class FunesoftResponseDTO  extends ResponseDTO {

    public FunesoftResponseDTO(boolean success, Object data, String message, Exception e) {
        super(success, data);

        List<String> errores = null;
        if(e != null) {
            errores = new ArrayList<>();
            errores.add(ExcepcionParser.getRootException(e).getMessage());
        }

        this.setMessage(message);
        this.setErrores(errores);
    }


}
