package com.funesoft.controller;

import com.funesoft.dto.SocioDTO;
import com.funesoft.model.Socio;
import com.funesoft.model.Zona;
import com.funesoft.repository.ZonaRepository;
import com.funesoft.utilities.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class ZonaController {

    @Autowired
    private ZonaRepository zonaRepository;

    public List<Zona> getAllZonas (){
        return zonaRepository.findAll();
    }

    public Zona insertZona (Zona zona){
        return zonaRepository.save(zona);
    }

    public Integer deleteZona(Zona zona) throws Exception {
        Optional<Zona> zonaDelete = zonaRepository.findById(zona.getIdZona());
        if(zonaDelete.isPresent()){
            zonaRepository.delete(zonaDelete.get());
            return zona.getIdZona();
        }else{
            throw new BusinessException("La zona especificada no existe");
        }
    }


}
