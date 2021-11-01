/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.controller;

import com.funesoft.dto.AdherenteBajaDTO;
import com.funesoft.dto.AdherenteDTO;
import com.funesoft.dto.RemoveAllBySocioDTO;
import com.funesoft.model.Adherente;
import com.funesoft.model.AdherenteBaja;
import com.funesoft.model.Estado;
import com.funesoft.model.MotivoBaja;
import com.funesoft.model.Socio;
import com.funesoft.repository.AdherenteBajaRepository;
import com.funesoft.repository.AdherenteRepository;
import com.funesoft.repository.EstadoRepository;
import com.funesoft.repository.MotivoBajaRepository;
import com.funesoft.repository.SocioRepository;
import com.funesoft.utilities.BusinessException;
import com.funesoft.utilities.CurrentUser;
import com.funesoft.utilities.EstadoEnum;
import java.util.List;
import java.util.NoSuchElementException;
import javax.persistence.NoResultException;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
    private CoberturaController coberturaController;
    
    @Autowired
    private MotivoBajaRepository motivoBajaRepository;
    
    @Autowired
    private AdherenteBajaRepository adherenteBajaRepository;
    
    public Adherente insertAdherente (@NotNull AdherenteDTO adherenteDTO){

        final Adherente adherente = new Adherente(adherenteDTO);
        System.out.println("llega");
        if (adherenteDTO.getFechaCobertura() == null) {
            //CALCULO LA COBERTURA
            adherente.setFechaCobertura(coberturaController.calculoCobertura(adherente));
            System.out.println("llega 2");
        } else {
            adherente.setFechaCobertura(adherenteDTO.getFechaCobertura());
        }
        System.out.println("llega 3");
        adherente.setUsuarioModifica(CurrentUser.getInstance());
        adherente.setEstado(estadoRepository.findByNroEstado(EstadoEnum.ALTA.getCodigo()));
        System.out.println("llega");
        return adherenteRepository.save(adherente);
    }
    
    public List<Adherente> getAll(Adherente adherente){
        return adherenteRepository.findAll(Example.of(adherente));
    }
    
    public List<Adherente> getAllOrderedBySocio(Integer dniSocio){
        if (dniSocio == null) {
            return adherenteRepository.findAllByOrderBySocioDniDesc();
        } else {
            return adherenteRepository.findByDniSocioLike(dniSocio);
        }
    }
    
    public Adherente updateAdherente (@NotNull Adherente adherente) throws BusinessException {
        if(!(adherenteRepository.findById(adherente.getId()).isPresent())){
            throw new BusinessException("El adherente informado no existe");
        }
        adherente.setUsuarioModifica(CurrentUser.getInstance());
        return adherenteRepository.save(adherente);
    }
    
    public Adherente deleteAdherente(@NotNull AdherenteBajaDTO adherenteBajaDTO) throws BusinessException {
        final Adherente adherente;
        try {
            adherente = adherenteRepository.findById(adherenteBajaDTO.getIdAdherente()).get();
        } catch (NoSuchElementException nex) {
            throw new BusinessException("El adherente informado no existe");
        }
        final MotivoBaja motivoBaja;
        try {
            motivoBaja = motivoBajaRepository.findById(adherenteBajaDTO.getIdMotivoBaja()).get();
        } catch (NoSuchElementException nex) {
            throw new BusinessException("El motivo informado no existe");
        }
        adherenteBajaRepository.save(new AdherenteBaja(adherente, motivoBaja, CurrentUser.getInstance()));
        adherente.setEstado(estadoRepository.findByNroEstado(EstadoEnum.BAJA.getCodigo()));
        adherente.setUsuarioModifica(CurrentUser.getInstance());
        return adherenteRepository.save(adherente);
    }
    
    public List<Adherente> removeAllBySocio(final RemoveAllBySocioDTO removeAllBySocioDTO) throws Exception {
        final Socio socioDB;
        try{
            socioDB = socioRepository.findById(removeAllBySocioDTO.getSocio().getId()).get();
        }catch (NoResultException | NoSuchElementException | NullPointerException exception){
            throw new BusinessException("El socio ingresado no existe.");
        }
        final List<Adherente> adherentes = adherenteRepository.findBySocioOrderByFechaNacimientoAsc(socioDB);
        // SI NO HAY ADHERENTES NO SE RETORNA ERROR YA QUE NO ME PARECE NECESARIO FRENAR EL FLUJO POR ALGO IRRELEVANTE
        for (Adherente adherente : adherentes){
            final Estado estadoBaja;
            try{
                estadoBaja = estadoRepository.findByNroEstado(EstadoEnum.BAJA.getCodigo());
            }catch (NoResultException | NoSuchElementException | NullPointerException exception){
                throw new BusinessException("Error al obtener estado de baja.");
            }
            adherente.setEstado(estadoBaja);
            adherenteRepository.save(adherente);
        }
        return adherentes;
    }
    
    public List<Adherente> insertAll(final List<Adherente> adherentesToInsert) {
        return adherenteRepository.saveAll(adherentesToInsert);
    }
    
}
