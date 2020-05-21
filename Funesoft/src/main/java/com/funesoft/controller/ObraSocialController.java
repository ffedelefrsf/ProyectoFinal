/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.controller;

import com.funesoft.model.ObraSocial;
import com.funesoft.repository.ObraSocialRepository;
import com.funesoft.utilities.BusinessException;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;

/**
 *
 * @author faust
 */
@Controller
public class ObraSocialController {
    
    @Autowired
    private ObraSocialRepository obraSocialRepository;
    
    public List<ObraSocial> getAllObrasSociales(final ObraSocial obraSocial){
        
        final List<ObraSocial> obrasSociales;
        
        obrasSociales = obraSocialRepository.findAll(Example.of(obraSocial));
        
        return obrasSociales;
    }
    
    public ObraSocial insertObraSocial(@NotNull final ObraSocial obraSocial) throws BusinessException{
        if (!obraSocialRepository.findById(obraSocial.getId()).isPresent()){
            try{
                return obraSocialRepository.save(obraSocial);
            }catch(Exception exception){
                throw new BusinessException(exception.getMessage());
            }
        }else{
            throw new BusinessException("La tarifa especificada ya existe.");
        }
    }
    
    public ObraSocial updateObraSocial(@NotNull final ObraSocial obraSocial) throws BusinessException{
        if (obraSocialRepository.findById(obraSocial.getId()).isPresent()){
            try{
                return obraSocialRepository.save(obraSocial);
            }catch(Exception exception){
                throw new BusinessException(exception.getMessage());
            }
        }else{
            throw new BusinessException("La tarifa especificada no existe.");
        }
    }
    
    public ObraSocial deleteObraSocial(@NotNull final Integer idObraSocial) throws BusinessException{
        final Optional<ObraSocial> optionalObraSocial;
        optionalObraSocial = obraSocialRepository.findById(idObraSocial);
        if (optionalObraSocial.isPresent()){
            try{
                obraSocialRepository.deleteById(idObraSocial);
                return optionalObraSocial.get();
            }catch (Exception exception){
                throw new BusinessException(exception.getMessage());
            }
        }else{
            throw new BusinessException("La tarifa especificada no existe.");
        }
    }
    
}
