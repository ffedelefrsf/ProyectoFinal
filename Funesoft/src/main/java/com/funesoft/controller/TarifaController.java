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
import com.funesoft.repository.SocioRepository;
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
 * @author faust
 */
@Controller
public class TarifaController {

    @Autowired
    private TarifaRepository tarifaRepository;

    @Autowired
    private RangoTarifaRepository rangoTarifaRepository;

    @Autowired
    private SocioRepository socioRepository;

    @Autowired
    private AdherenteRepository adherenteRepository;

    @Autowired
    private RangoTarifaController rangoTarifaController;

    public List<Tarifa> getAllTarifas(final TarifaDTO tarifaDTO) {

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

    public Map<Integer, Float> getValorTarifaByAsociado(@NotNull final Socio socio) throws BusinessException {
        final Tarifa tarifa;
        try {
            tarifa = socioRepository.findById(socio.getId()).get().getTarifa();
        } catch (NoResultException nrex) {
            throw new BusinessException("El socio no existe.");
        }
        if (tarifa == null) {
            throw new BusinessException("La tarifa no existe");
        }
        // OBTENGO TODOS LOS RANGOS DE LA TARIFA
        final List<RangoTarifa> rangosTarifa = rangoTarifaRepository.findByTarifa(tarifa);

        final Plan plan = tarifa.getPlan();
        // ARMO UN MAP CON <DNI, VALOR A PAGAR>, SI DNI = 0 -> TOTAL
        final Map<Integer, Float> mapSalida = new HashMap();
        switch (plan.getDescripcion()) {
            case Plan.INDIVIDUAL:
                try {
                    Float total = 0F;
                    for (RangoTarifa rango : rangosTarifa) {
                        final Short edad = socio.getEdad();
                        if (rango.getEdadDesde() <= edad && rango.getEdadHasta() >= edad) {
                            mapSalida.put(socio.getDni(), rango.getValor());
                            total += rango.getValor();
                        }
                        mapSalida.put(0, total);
                    }
                    return mapSalida;
                } catch (Exception exception) {
                    throw new BusinessException(exception.getMessage());
                }
            case Plan.TITULAR_Y_ADHERENTE:
                try {
                    final Adherente adherente = adherenteRepository.findBySocioOrderByFechaNacimientoAsc(socio).get(0);
                    Float total = 0F;
                    for (RangoTarifa rango : rangosTarifa) {
                        final Short edadSocio = socio.getEdad(), edadAdherente = adherente.getEdad();
                        if (rango.getEdadDesde() <= edadSocio && rango.getEdadHasta() >= edadSocio) {
                            total += rango.getValor();
                            mapSalida.put(socio.getDni(), rango.getValor());
                        }
                        if (rango.getEdadDesde() <= edadAdherente && rango.getEdadHasta() >= edadAdherente) {
                            total += rango.getValor();
                            mapSalida.put(adherente.getDni(), rango.getValor());
                        }
                        mapSalida.put(0, total);
                    }
                    return mapSalida;
                } catch (Exception exception) {
                    throw new BusinessException(exception.getMessage());
                }
            case Plan.FAMILIAR:
                try {
                    final List<Adherente> adherentes = adherenteRepository.findBySocioOrderByFechaNacimientoAsc(socio);
                    Float total = 0F;

                    //ESTE VALOR ABARCA EL SOCIO Y EL PRIMER ADHERENTE
                    mapSalida.put(socio.getDni(), tarifa.getValor());

                    int iterator = 0;

                    for (Adherente adh : adherentes) {

                        if (iterator == 0) {
                            mapSalida.put(adh.getDni(), 0F);
                        } else {
                            final Short edadAdherente = adh.getEdad();
                            for (RangoTarifa rango : rangosTarifa) {
                                if (rango.getEdadDesde() <= edadAdherente && rango.getEdadHasta() >= edadAdherente) {
                                    total += rango.getValor();
                                    mapSalida.put(adh.getDni(), rango.getValor());
                                }
                            }
                        }
                        iterator++;
                    }
                    mapSalida.put(0, total + tarifa.getValor());
                    return mapSalida;
                } catch (Exception exception) {
                    throw new BusinessException(exception.getMessage());
                }
            default:
                throw new BusinessException("El plan no es válido.");
        }
    }

    public Tarifa insertTarifa(@NotNull final TarifaDTO tarifaDTO) throws BusinessException {
        try {

            Tarifa tarifa = tarifaRepository.save(new Tarifa(tarifaDTO));

            //CARGO LOS RANGOS
            if (rangoTarifaController.insertRango(tarifa, tarifaDTO.getListRango()) != null) {
                return tarifa;
            } else {
                throw new BusinessException("No fue posible guardar los rangos de la tarifa. Intente nuevamente");
            }

        } catch (Exception exception) {
            throw new BusinessException(exception.getMessage());
        }
    }

    public Tarifa updateTarifa(@NotNull final Tarifa tarifa) throws BusinessException {
        if (tarifaRepository.findById(tarifa.getId()).isPresent()) {
            try {
                return tarifaRepository.save(tarifa);
            } catch (Exception exception) {
                throw new BusinessException(exception.getMessage());
            }
        } else {
            throw new BusinessException("La tarifa especificada no existe.");
        }
    }

    public Integer deleteTarifa(@NotNull final Integer idTarifa) throws BusinessException {
        if (tarifaRepository.findById(idTarifa).isPresent()) {
            try {
                tarifaRepository.deleteById(idTarifa);
                return idTarifa;
            } catch (Exception exception) {
                throw new BusinessException(exception.getMessage());
            }
        } else {
            throw new BusinessException("La tarifa especificada no existe.");
        }
    }

}
