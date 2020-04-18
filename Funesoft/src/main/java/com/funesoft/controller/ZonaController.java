package com.funesoft.controller;

import com.funesoft.dto.ZonaDTO;
import com.funesoft.model.Zona;
import com.funesoft.repository.ZonaRepository;
import com.funesoft.utilities.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotNull;

@Controller
public class ZonaController {

    @Autowired
    private ZonaRepository zonaRepository;

    public List<Zona> getZonas (Zona zona){

        List<Zona> result = zonaRepository.findAll(Example.of(zona));

        return result;
    }

    public Zona insertZona (@NotNull Zona zona){
        return zonaRepository.save(zona);
    }

    public Zona updateZona (@NotNull Zona zona) throws BusinessException {
        Optional<Zona> zonaDelete = zonaRepository.findById(zona.getId());
        if(zonaDelete.isPresent()){
            return zonaRepository.save(zona);
        }else{
            throw new BusinessException("La zona especificada no existe");
        }
    }

    public Zona deleteZona(@NotNull Integer idZona) throws BusinessException {
        Optional<Zona> zonaDelete = zonaRepository.findById(idZona);
        if(zonaDelete.isPresent()){
            zonaRepository.delete(zonaDelete.get());
            return zonaDelete.get();
        }else{
            throw new BusinessException("La zona especificada no existe");
        }
    }


}
