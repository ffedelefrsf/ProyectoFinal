package com.funesoft.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.funesoft.utilities.ExcepcionParser;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FunesoftResponseDTO implements Serializable {

    private boolean success;
    private Object data;
    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Buenos_Aires")
    private Date date = new Date();

    private List<String> errores;

    public FunesoftResponseDTO(boolean success, Object data, String message, Date date, List<String> errores) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.date = date;
        this.errores = errores;
    }

    public FunesoftResponseDTO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public FunesoftResponseDTO(boolean success, Object data) {
        this.success = success;
        this.data = data;
    }
    
    public FunesoftResponseDTO(boolean success, Object data, String message, Exception e) {
        this.success = success;
        this.data = data;

        List<String> errores = null;
        if(e != null) {
            errores = new ArrayList<>();
            errores.add(ExcepcionParser.getRootException(e).getMessage());
        }

        this.setMessage(message);
        this.setErrores(errores);
    }

    @Override
    public String toString() {
        return "FunesoftResponseDTO{" + "success=" + success + ", data=" + data + ", message=" + message + ", date=" + date + ", errores=" + errores + '}';
    }
    
}
