/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.controller;

import com.funesoft.dto.SocioBajaDTO;
import com.funesoft.dto.SocioDTO;
import com.funesoft.model.*;
import com.funesoft.repository.EstadoRepository;
import com.funesoft.repository.MotivoBajaRepository;
import com.funesoft.repository.SocioBajaRepository;
import com.funesoft.repository.SocioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.funesoft.utilities.BusinessException;
import com.funesoft.utilities.CurrentUser;
import com.funesoft.utilities.EstadoEnum;
import javax.validation.constraints.NotNull;

import jdk.nashorn.internal.runtime.options.Option;
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
    private MotivoBajaRepository motivoBajaRepository;

    @Autowired
    private SocioBajaRepository socioBajaRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Socio> getSocios (Socio socio){
        return socioRepository.findAll(Example.of(socio));
    }

    public Socio insertSocio (@NotNull SocioDTO socioDTO){

        Socio socio = new Socio(socioDTO);

        //CALCULO LA COBERTURA
        socio.setFechaCobertura(coberturaController.calculoCobertura(socioDTO.getFechaNacimiento()));

        socio.setUsuarioModifica(CurrentUser.getInstance());
        socio.setEstado(estadoRepository.findByNroEstado(EstadoEnum.ALTA.getCodigo()));

        return socioRepository.save(socio);
    }

    public Socio updateSocio (@NotNull Socio socio) throws BusinessException {
        if(!(socioRepository.findById(socio.getId()).isPresent())){
            throw new BusinessException("El socio informado no existe");
        }
        socio.setUsuarioModifica(CurrentUser.getInstance());
        return socioRepository.save(socio);
    }

    public Socio deleteSocio (@NotNull SocioBajaDTO socioBajaDTO) throws BusinessException {

        //SE BUSCA EL SOCIO POR EL ID
        Optional<Socio> socio = socioRepository.findById(socioBajaDTO.getIdSocio());
        Optional<MotivoBaja> motivoBaja = motivoBajaRepository.findById(socioBajaDTO.getIdMotivoBaja());

        if(!socio.isPresent()) {
            throw new BusinessException("El socio informado no existe");
        }

        if(!motivoBaja.isPresent()) {
            throw new BusinessException("El motivo informado no existe");
        }

        //ACTUALIZO EL ESTADO DEL SOCIO
        socio.get().setEstado(estadoRepository.findByNroEstado(EstadoEnum.BAJA.getCodigo()));
        socio.get().setUsuarioModifica(CurrentUser.getInstance());

        //CARGO EL MOTIVO DE LA BAJA
        socioBajaRepository.save(
                new SocioBaja(
                        socio.get(),
                        motivoBaja.get(),
                        CurrentUser.getInstance()
                )
        );

        return socioRepository.save(socio.get());
    }
}
