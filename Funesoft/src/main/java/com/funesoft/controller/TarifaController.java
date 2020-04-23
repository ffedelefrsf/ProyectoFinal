/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.controller;

import com.funesoft.dto.TarifaDTO;
import com.funesoft.model.Adherente;
import com.funesoft.model.Plan;
import com.funesoft.model.RangoTarifa;
import com.funesoft.model.Socio;
import com.funesoft.model.Tarifa;
import com.funesoft.repository.AdherenteRepository;
import com.funesoft.repository.RangoTarifaRepository;
import com.funesoft.repository.TarifaRepository;
import com.funesoft.utilities.BusinessException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class TarifaController {
    
    @Autowired
    private TarifaRepository tarifaRepository;
    
    @Autowired
    private RangoTarifaRepository rangoTarifaRepository;
    
    @Autowired
    private AdherenteRepository adherenteRepository;
    
    public List<Tarifa> getAllTarifas(final TarifaDTO tarifaDTO){
        
        final Tarifa tarifa = new Tarifa(tarifaDTO);
        
        final List<Tarifa> tarifas;
//        final Integer pagina = tarifaDTO.getPagina(), cantidad = tarifaDTO.getCantidad();
//        final String sortField = tarifaDTO.getSortField();
//        final Boolean sortWay = tarifaDTO.getSortWay();
//        if (pagina != null && cantidad != null){
//            if (sortField != null && sortWay != null){
//                if (sortWay){
//                    tarifas = tarifaRepository.findAll(Example.of(tarifa), PageRequest.of(pagina, cantidad, Sort.by(sortField).ascending())).getContent();
//                }else{
//                    tarifas = tarifaRepository.findAll(Example.of(tarifa), PageRequest.of(pagina, cantidad, Sort.by(sortField).descending())).getContent();
//                }
//            }else{
//                tarifas = tarifaRepository.findAll(Example.of(tarifa), PageRequest.of(pagina, cantidad)).getContent();
//            }
//        }else{
            tarifas = tarifaRepository.findAll(Example.of(tarifa));
//        }
        
        return tarifas;
    }
    
    public Map<Integer, Float> getValorTarifaByAsociado(@NotNull final Socio socio) throws BusinessException{
        final Tarifa tarifa;
        try{
            tarifa = tarifaRepository.findBySocio(socio);
        }catch (NoResultException nrex){
            throw new BusinessException("El socio no existe.");
        }
        if (tarifa == null){
            throw new BusinessException("El socio no existe.");
        }
        // OBTENGO TODOS LOS RANGOS DE LA TARIFA
        final List<RangoTarifa> rangosTarifa = rangoTarifaRepository.findByTarifa(tarifa);
        
        final Plan plan = tarifa.getPlan();
        // ARMO UN MAP CON <DNI, VALOR A PAGAR>, SI DNI = 0 -> TOTAL
        final Map<Integer, Float> mapSalida = new HashMap();
        switch (plan.getDescripcion()){
            case Plan.INDIVIDUAL:
                try{
                    for (RangoTarifa rango : rangosTarifa){
                        final Short edad = socio.getEdad();
                        Float total = 0F;
                        if (rango.getEdadDesde() <= edad && rango.getEdadHasta() >= edad){
                            mapSalida.put(socio.getId(), rango.getValor());
                            total += rango.getValor();
                        }
                        mapSalida.put(0, total);
                    }
                    return mapSalida;
                }catch(Exception exception){
                    throw new BusinessException(exception.getMessage());
                }
            case Plan.TITULAR_Y_ADHERENTE:
                try{
                    final Adherente adherente = adherenteRepository.findBySocio(socio).get(0);
                    for (RangoTarifa rango : rangosTarifa){
                        final Short edadSocio = socio.getEdad(), edadAdherente = adherente.getEdad();
                        Float total = 0F;
                        if (rango.getEdadDesde() <= edadSocio && rango.getEdadHasta() >= edadSocio){
                            total += rango.getValor();
                            mapSalida.put(socio.getDni(), rango.getValor());
                        }
                        if (rango.getEdadDesde() <= edadAdherente && rango.getEdadHasta() >= edadAdherente){
                            total += rango.getValor();
                            mapSalida.put(adherente.getDni(), rango.getValor());
                        }
                        mapSalida.put(0, total);
                    }
                    return mapSalida;
                }catch(Exception exception){
                    throw new BusinessException(exception.getMessage());
                }
            case Plan.FAMILIAR:
                try{
                    final List<Adherente> adherentes = adherenteRepository.findBySocio(socio);
                    Float total = 0F;
                    final Short edadSocio = socio.getEdad();
                    for (RangoTarifa rango : rangosTarifa){
                        if (rango.getEdadDesde() <= edadSocio && rango.getEdadHasta() >= edadSocio){
                            total += rango.getValor();
                            mapSalida.put(socio.getDni(), rango.getValor());
                        }
                    }
                    for (Adherente adh : adherentes){
                        final Short edadAdherente = adh.getEdad();
                        for (RangoTarifa rango : rangosTarifa){
                            if (rango.getEdadDesde() <= edadAdherente && rango.getEdadHasta() >= edadAdherente){
                                total += rango.getValor();
                                mapSalida.put(adh.getDni(), rango.getValor());
                            }
                        }
                    }
                    mapSalida.put(0, total);
                    return mapSalida;
                }catch(Exception exception){
                    throw new BusinessException(exception.getMessage());
                }
            default:
                throw new BusinessException("El plan no es válido.");
        }
    }
    
    public Tarifa insertTarifa(@NotNull final Tarifa tarifa) throws BusinessException{
        if (!tarifaRepository.findById(tarifa.getId()).isPresent()){
            try{
                return tarifaRepository.save(tarifa);
            }catch(Exception exception){
                throw new BusinessException(exception.getMessage());
            }
        }else{
            throw new BusinessException("La tarifa especificada ya existe.");
        }
    }
    
    public Tarifa updateTarifa(@NotNull final Tarifa tarifa) throws BusinessException{
        if (tarifaRepository.findById(tarifa.getId()).isPresent()){
            try{
                return tarifaRepository.save(tarifa);
            }catch(Exception exception){
                throw new BusinessException(exception.getMessage());
            }
        }else{
            throw new BusinessException("La tarifa especificada no existe.");
        }
    }
    
    public Integer deleteTarifa(@NotNull final Integer idTarifa) throws BusinessException{
        if (tarifaRepository.findById(idTarifa).isPresent()){
            try{
                tarifaRepository.deleteById(idTarifa);
                return idTarifa;
            }catch (Exception exception){
                throw new BusinessException(exception.getMessage());
            }
        }else{
            throw new BusinessException("La tarifa especificada no existe.");
        }
    }
    
}
