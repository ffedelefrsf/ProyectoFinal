package com.funesoft.dto;

import com.funesoft.model.Socio;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ComprobanteDTO {
    private Integer id;
    private BigInteger nroComprobante;
    private Double importeTotal;
    private Boolean impreso;
    private Socio socio;
    private Boolean pagado;
    private String vencimiento;
}
