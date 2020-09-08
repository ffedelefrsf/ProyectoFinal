package com.funesoft.dto;

import com.funesoft.model.Cobrador;
import com.funesoft.model.Usuario;
import com.funesoft.model.Zona;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ZonaCobradoresDTO {

    Zona zona;
    List<Cobrador> cobradores;

}
