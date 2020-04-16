package com.funesoft.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDTO implements Serializable {

    private boolean success;
    private Object data;
    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Buenos_Aires")
    private Date date = new Date();

    private List<String> errores;

    public ResponseDTO() {
    }

    public ResponseDTO(boolean success, Object data, String message, Date date, List<String> errores) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.date = date;
        this.errores = errores;
    }

    public ResponseDTO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ResponseDTO(boolean success, Object data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<String> getErrores() {
        return errores;
    }

    public void setErrores(List<String> errores) {
        this.errores = errores;
    }
}
