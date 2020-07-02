package com.funesoft.dto;

import com.funesoft.model.Plan;
import com.funesoft.model.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RangoTarifaDTO {

        private Integer edadDesde;
        private Integer edadHasta;
        private Double valor;
        private Usuario usuarioModifica;

}
