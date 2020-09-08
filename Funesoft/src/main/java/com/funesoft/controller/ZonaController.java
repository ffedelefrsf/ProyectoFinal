package com.funesoft.controller;

import com.funesoft.dto.ZonaCobradoresDTO;
import com.funesoft.dto.ZonaDTO;
import com.funesoft.model.Cobrador;
import com.funesoft.model.Zona;
import com.funesoft.model.ZonaCobrador;
import com.funesoft.repository.CobradorRepository;
import com.funesoft.repository.ZonaCobradorRepository;
import com.funesoft.repository.ZonaRepository;
import com.funesoft.utilities.BusinessException;
import com.funesoft.utilities.CurrentUser;
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

    @Autowired
    private ZonaCobradorRepository zonaCobradorRepository;

    @Autowired
    private ZonaCobradorController zonaCobradorController;

    public List<Zona> getZonas (Zona zona){
        return zonaRepository.findAll(Example.of(zona));
    }

    public Zona insertZona (@NotNull ZonaCobradoresDTO zonaCobradoresDTO){
        zonaCobradoresDTO.getZona().setUsuarioModifica(CurrentUser.getInstance());
        Zona zona = zonaRepository.save(zonaCobradoresDTO.getZona());

        for (Cobrador cobrador : zonaCobradoresDTO.getCobradores()) {
            ZonaCobrador zonaCobrador = new ZonaCobrador();
            zonaCobrador.setZona(zonaCobradoresDTO.getZona());
            zonaCobrador.setCobrador(cobrador);
            zonaCobradorController.insertZonaCobrador(zonaCobrador);
        }

        return zona;
    }

    public Zona updateZona (@NotNull Zona zona) throws BusinessException {
        Optional<Zona> zonaUpdate = zonaRepository.findById(zona.getId());
        if(!zonaUpdate.isPresent()){
            throw new BusinessException("La zona especificada no existe");
        }
        zona.setUsuarioModifica(CurrentUser.getInstance());
        return zonaRepository.save(zona);
    }

    public Zona deleteZona(@NotNull Integer idZona) throws BusinessException {
        Optional<Zona> zonaDelete = zonaRepository.findById(idZona);
        if(!zonaDelete.isPresent()){
            throw new BusinessException("La zona especificada no existe");
        }
        zonaDelete.get().setUsuarioModifica(CurrentUser.getInstance());
        zonaRepository.delete(zonaRepository.save(zonaDelete.get()));
        return zonaDelete.get();
    }


}
