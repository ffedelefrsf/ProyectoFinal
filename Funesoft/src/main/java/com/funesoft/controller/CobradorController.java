package com.funesoft.controller;

import com.funesoft.dto.CobradorDTO;
import com.funesoft.dto.LocalidadDTO;
import com.funesoft.model.Cobrador;
import com.funesoft.model.Localidad;
import com.funesoft.model.Zona;
import com.funesoft.repository.CobradorRepository;
import com.funesoft.repository.LocalidadRepository;
import com.funesoft.utilities.BusinessException;
import com.funesoft.utilities.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class CobradorController {

    @Autowired
    private CobradorRepository cobradorRepository;

    public List<Cobrador> getAllCobradores(Cobrador cobrador){
        return cobradorRepository.findAll(Example.of(cobrador));
    }

    public Cobrador insertCobrador (@NotNull CobradorDTO cobradorDTO){
        Cobrador cobrador = new Cobrador(cobradorDTO);
        cobrador.setUsuarioModifica(CurrentUser.getInstance());
        return cobradorRepository.save(cobrador);
    }

    public Cobrador updateCobrador (@NotNull Cobrador cobrador) throws BusinessException {
        Optional<Cobrador> cobradorUpdate = cobradorRepository.findById(cobrador.getId());
        if(!cobradorUpdate.isPresent()){
            throw new BusinessException("El cobrador informado no existe");
        }
        cobrador.setUsuarioModifica(CurrentUser.getInstance());
        return cobradorRepository.save(cobrador);
    }

    public Cobrador deleteCobrador (@NotNull Integer idCobrador) throws BusinessException {
        Optional<Cobrador> cobradorDelete = cobradorRepository.findById(idCobrador);
        if(!cobradorDelete.isPresent()){
            throw new BusinessException("El cobrador informado no existe");
        }
        cobradorDelete.get().setUsuarioModifica(CurrentUser.getInstance());
        cobradorRepository.delete(cobradorRepository.save(cobradorDelete.get()));
        return cobradorDelete.get();
    }

}
