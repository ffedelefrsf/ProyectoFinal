package com.funesoft.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PagoDTO {

    @NotNull
    private Double valor;

    @NotNull
    private Integer idSocio;

    @NotNull
    private Integer idCobrador;

    @NotNull
    private Integer idComprobante;

    public PagoDTO(Double valor, Integer idSocio, Integer idCobrador, Integer idComprobante) {
        this.valor = valor;
        this.idSocio = idSocio;
        this.idCobrador = idCobrador;
        this.idComprobante = idComprobante;
    }

}
