/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.controller;

import com.funesoft.dto.SocioDTO;
import com.funesoft.model.Socio;
import com.funesoft.repository.SocioRepository;
import java.util.List;
import java.util.Optional;
import com.funesoft.utilities.BusinessException;
import com.funesoft.utilities.EstadoEnum;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;

import javax.validation.constraints.NotNull;

/**
 *
 * @author faust
 */
@Controller
public class SocioController {
    
    @Autowired
    private SocioRepository socioRepository;

    @Autowired
    private CoberturaController coberturaController;

    @Autowired
    private HistorialEstadoSocioController historialEstadoSocioController;

    public List<Socio> getSocios (Socio socio){
        return socioRepository.findAll(Example.of(socio));
    }

    public Socio insertSocio (@NotNull SocioDTO socioDTO){

        Socio socio = new Socio(socioDTO);

        //CALCULO LA COBERTURA
        socio.setFechaCobertura(coberturaController.calculoCobertura(socioDTO.getFechaNacimiento()));

        Socio nuevoSocio = socioRepository.save(socio);

        //INSERTO EL HISTORIAL DEL SOCIO (ALTA)
        historialEstadoSocioController.insertHistorial(nuevoSocio, EstadoEnum.ALTA, null);

        return nuevoSocio;

    }

    public Socio updateSocio (@NotNull Socio socio) throws BusinessException {
        if(!(socioRepository.findById(socio.getId()).isPresent())){
            throw new BusinessException("El socio informado no existe");
        }
        return socioRepository.save(socio);
    }

    public Socio deleteSocio (@NotNull Integer idSocio, @NotNull Integer motivoBaja) throws BusinessException {

        //SE BUSCA EL SOCIO POR EL ID
        Optional<Socio> socio = socioRepository.findById(idSocio);

        if(!socio.isPresent()) {
            throw new BusinessException("El socio informado no existe");
        }

        //Cambio el estado del socio a Estado BAJA
        historialEstadoSocioController.insertHistorial(socio.get(), EstadoEnum.BAJA, motivoBaja);

        return socio.get();

    }

}
