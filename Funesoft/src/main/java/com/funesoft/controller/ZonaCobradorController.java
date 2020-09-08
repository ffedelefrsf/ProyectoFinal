package com.funesoft.controller;

import com.funesoft.model.ZonaCobrador;
import com.funesoft.repository.ZonaCobradorRepository;
import com.funesoft.utilities.BusinessException;
import com.funesoft.utilities.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Controller
public class ZonaCobradorController {

    @Autowired
    ZonaCobradorRepository zonaCobradorRepository;

    public List<ZonaCobrador> getZonaCobrador(final ZonaCobrador zonaCobrador){
        return zonaCobradorRepository.findAll(Example.of(zonaCobrador));
    }

    public ZonaCobrador insertZonaCobrador (@NotNull ZonaCobrador zonaCobrador){
        zonaCobrador.setUsuarioModifica(CurrentUser.getInstance());
        return zonaCobradorRepository.save(zonaCobrador);
    }

    public ZonaCobrador updateZonaCobrador (@NotNull ZonaCobrador zonaCobrador) throws BusinessException {
        Optional<ZonaCobrador> zonaCobradorUpdate = zonaCobradorRepository.findById(zonaCobrador.getId());
        if(!zonaCobradorUpdate.isPresent()){
            throw new BusinessException("La zona-cobrador informada no existe");
        }
        zonaCobradorUpdate.get().setUsuarioModifica(CurrentUser.getInstance());
        return zonaCobradorRepository.save(zonaCobradorUpdate.get());
    }

    public ZonaCobrador deleteZonaCobrador (@NotNull Integer idZonaCobrador) throws BusinessException {
        Optional<ZonaCobrador> zonaCobradorDelete = zonaCobradorRepository.findById(idZonaCobrador);
        if(!zonaCobradorDelete.isPresent()){
            throw new BusinessException("La zona-cobrador informada no existe");
        }
        zonaCobradorDelete.get().setUsuarioModifica(CurrentUser.getInstance());
        zonaCobradorRepository.delete(zonaCobradorRepository.save(zonaCobradorDelete.get()));
        return zonaCobradorDelete.get();
    }

}
