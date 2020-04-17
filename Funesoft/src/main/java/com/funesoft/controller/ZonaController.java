package com.funesoft.controller;

import com.funesoft.dto.ZonaDTO;
import com.funesoft.model.Zona;
import com.funesoft.repository.ZonaRepository;
import com.funesoft.utilities.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;

import javax.swing.text.html.Option;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Controller
public class ZonaController {

    @Autowired
    private ZonaRepository zonaRepository;

    public List<Zona> getZonas (Zona zona){
        return zonaRepository.findAll(Example.of(zona));
    }

    public Zona insertZona (@NotNull Zona zona){
        return zonaRepository.save(zona);
    }

    public Zona updateZona (@NotNull Zona zona) throws BusinessException {
        if(!(zonaRepository.findById(zona.getId())).isPresent()){
            throw new BusinessException("La zona informada no existe");
        }

        return zonaRepository.save(zona);
    }

    public Zona deleteZona(@NotNull Integer idZona) throws BusinessException {
        Optional<Zona> zonaDelete = zonaRepository.findById(idZona);

        if(!zonaDelete.isPresent()){
            throw new BusinessException("La zona especificada no existe");
        }

        zonaRepository.delete(zonaDelete.get());
        return zonaDelete.get();
    }


}
