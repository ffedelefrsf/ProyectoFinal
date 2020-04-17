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

    public List<Zona> getZonas (ZonaDTO zonaDTO) throws BusinessException {

        Zona zona = new Zona(zonaDTO);
        List<Zona> result = zonaRepository.findAll(Example.of(zona));

        if(result.size() <= 0){
            throw new BusinessException("No existen zonas con los parametros informados");
        }

        return result;
    }

    public Zona insertZona (@NotNull Zona zona){
        return zonaRepository.save(zona);
    }

    public Integer deleteZona(@NotNull Zona zona) throws Exception {
        Optional<Zona> zonaDelete = zonaRepository.findById(zona.getId());
        if(zonaDelete.isPresent()){
            zonaRepository.delete(zonaDelete.get());
            return zona.getId();
        }else{
            throw new BusinessException("La zona especificada no existe");
        }
    }


}
