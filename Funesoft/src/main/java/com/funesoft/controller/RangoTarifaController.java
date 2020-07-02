package com.funesoft.controller;

import com.funesoft.dto.RangoTarifaDTO;
import com.funesoft.model.RangoTarifa;
import com.funesoft.model.Tarifa;
import com.funesoft.repository.RangoTarifaRepository;
import com.funesoft.utilities.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class RangoTarifaController {

    @Autowired
    private RangoTarifaRepository rangoTarifaRepository;

    public List<RangoTarifa> getRangos(Tarifa tarifa){
        return rangoTarifaRepository.findByTarifa(tarifa);
    }

    public List<RangoTarifa> insertRango(Tarifa tarifa, List<RangoTarifa> rangos) {

        rangos.forEach(rango -> rangoTarifaRepository.save(new RangoTarifa(rango, tarifa, CurrentUser.getInstance())));

        return rangos;
    }

}
