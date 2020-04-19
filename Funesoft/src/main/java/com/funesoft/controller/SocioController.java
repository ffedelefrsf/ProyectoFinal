/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.controller;

import com.funesoft.dto.SocioBajaDTO;
import com.funesoft.dto.SocioDTO;
import com.funesoft.model.*;
import com.funesoft.repository.MotivoBajaRepository;
import com.funesoft.repository.SocioBajaRepository;
import com.funesoft.repository.SocioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.funesoft.utilities.BusinessException;
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
    private HistorialEstadoSocioController historialEstadoSocioController;

    @Autowired
    private MotivoBajaRepository motivoBajaRepository;

    @Autowired
    private SocioBajaRepository socioBajaRepository;

    public List<SocioDTO> getSocios (Socio socio){
        List<Socio> listaSocios = socioRepository.findAll(Example.of(socio));
        List<SocioDTO> result = new ArrayList<SocioDTO>();

        for(Socio item : listaSocios){
            SocioDTO dto = new SocioDTO(
                    item,
                    historialEstadoSocioController.getEstado(item).get().getEstado().getId()
            );

            result.add(dto);
        }

        return result;

    }

    public Socio insertSocio (@NotNull SocioDTO socioDTO){

        Socio socio = new Socio(socioDTO);

        //CALCULO LA COBERTURA
        socio.setFechaCobertura(coberturaController.calculoCobertura(socioDTO.getFechaNacimiento()));

        Socio nuevoSocio = socioRepository.save(socio);

        //INSERTO EL HISTORIAL DEL SOCIO (ALTA)
        historialEstadoSocioController.insertHistorial(nuevoSocio, EstadoEnum.ALTA);

        return nuevoSocio;

    }

    public Socio updateSocio (@NotNull Socio socio) throws BusinessException {
        if(!(socioRepository.findById(socio.getId()).isPresent())){
            throw new BusinessException("El socio informado no existe");
        }
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

        //CAMBIO EL ESTADO A 'BAJA'
        historialEstadoSocioController.insertHistorial(
                socio.get(),
                EstadoEnum.BAJA
        );

        //CARGO EL MOTIVO DE LA BAJA
        socioBajaRepository.save(
                new SocioBaja(
                        socio.get(),
                        motivoBaja.get()
                )
        );

        //FALTA VERIFICAR SI EL PRIMER ADHERENTE PASA A SER SOCIO DEPENDIENDO SI EL PLAN
        //ES DECIR SI TIENE ADHERENTES O NO
        //SE DEBERÍA TOMAR COMO SOCIO AL ADHERENTE MÁS PRÓXIMO O EL MAYOR DE EDAD
        //O VER SI SE LE PREGUNTA A LOS MISMOS.

        return socio.get();

    }

}
