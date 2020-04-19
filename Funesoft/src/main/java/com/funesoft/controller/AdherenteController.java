/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.controller;

import com.funesoft.dto.RemoveAllBySocioDTO;
import com.funesoft.model.Adherente;
import com.funesoft.model.Estado;
import com.funesoft.model.HistorialEstadoAdherente;
import com.funesoft.model.Socio;
import com.funesoft.repository.AdherenteRepository;
import com.funesoft.repository.EstadoRepository;
import com.funesoft.repository.HistorialEstadoAdherenteRepository;
import com.funesoft.repository.SocioRepository;
import com.funesoft.utilities.BusinessException;
import com.funesoft.utilities.CurrentUser;
import com.funesoft.utilities.EstadoEnum;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;
import javax.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author faust
 */
@Controller
public class AdherenteController {
    
    @Autowired
    private AdherenteRepository adherenteRepository;
    
    @Autowired
    private SocioRepository socioRepository;
    
    @Autowired
    private EstadoRepository estadoRepository;
    
    @Autowired
    private HistorialEstadoAdherenteRepository historialEstadoAdherenteRepository;
    
    public List<Adherente> removeAllBySocio(final RemoveAllBySocioDTO removeAllBySocioDTO) throws Exception {
        final Socio socioDB;
        try{
            socioDB = socioRepository.findById(removeAllBySocioDTO.getSocio().getId()).get();
        }catch (NoResultException | NoSuchElementException | NullPointerException exception){
            throw new BusinessException("El socio ingresado no existe.");
        }
        final List<Adherente> adherentes = adherenteRepository.findBySocio(socioDB);
        // SI NO HAY ADHERENTES NO SE RETORNA ERROR YA QUE NO ME PARECE NECESARIO FRENAR EL FLUJO POR ALGO IRRELEVANTE
        for (Adherente adherente : adherentes){
            final Estado estadoBaja;
            try{
                estadoBaja = estadoRepository.findByNroEstado(EstadoEnum.BAJA.getCodigo());
                final HistorialEstadoAdherente historialEstadoAdherente = new HistorialEstadoAdherente(Calendar.getInstance().getTime(), removeAllBySocioDTO.getMotivoBaja(), adherente, estadoBaja, CurrentUser.getInstance());
                historialEstadoAdherenteRepository.save(historialEstadoAdherente);
            }catch (NoResultException | NoSuchElementException | NullPointerException exception){
                throw new BusinessException("Error al obtener estado de baja.");
            }
            adherente.setEstado(estadoBaja);
            adherenteRepository.save(adherente);
        }
        return adherentes;
    }
    
}
