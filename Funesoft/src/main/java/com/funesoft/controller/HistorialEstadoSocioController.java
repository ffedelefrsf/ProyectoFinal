package com.funesoft.controller;

import com.funesoft.model.Estado;
import com.funesoft.model.HistorialEstadoSocio;
import com.funesoft.model.MotivoBaja;
import com.funesoft.model.Socio;
import com.funesoft.repository.EstadoRepository;
import com.funesoft.repository.HistorialEstadoSocioRepository;
import com.funesoft.repository.MotivoBajaRepository;
import com.funesoft.utilities.EstadoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Controller
public class HistorialEstadoSocioController {

    @Autowired
    private HistorialEstadoSocioRepository historialSocioRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    public HistorialEstadoSocio insertHistorial(Socio socio, EstadoEnum estadoEnum){
//        return historialSocioRepository.save(
//                new HistorialEstadoSocio(
//                    new Date(),
//                    socio,
//                    estadoRepository.findByNroEstado(estadoEnum.getCodigo())
//                )
//        );
        return null;
    }

    public Optional<HistorialEstadoSocio> getEstado(Socio socio){
        return historialSocioRepository.findFirstBySocioOrderByFechaAlta(socio);
    }

}
