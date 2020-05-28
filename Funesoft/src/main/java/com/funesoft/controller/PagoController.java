package com.funesoft.controller;

import com.funesoft.dto.PagoDTO;
import com.funesoft.model.Comprobante;
import com.funesoft.model.Pago;
import com.funesoft.model.Socio;
import com.funesoft.repository.ComprobanteRepository;
import com.funesoft.repository.PagoRepository;
import com.funesoft.repository.SocioRepository;
import com.funesoft.utilities.BusinessException;
import com.funesoft.utilities.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class PagoController {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private SocioRepository socioRepository;

    @Autowired
    private ComprobanteRepository comprobanteRepository;

    public Pago informarPago(PagoDTO pagoDTO) throws BusinessException {

        Pago pago = new Pago(pagoDTO);

        //INCREMENTO EL SALDO DEL SOCIO
        Optional<Socio> socio = socioRepository.findById(pago.getSocio().getId());

        if(!socio.isPresent()){
            throw new BusinessException("El socio informado no existe en la base de datos");
        }

        socio.get().setSaldo(
                socio.get().getSaldo() + pago.getValor()
        );

        //CORROBORO QUE EL CBTE QUE LLEGA ES DEL SOCIO
        Optional<Comprobante> cbte = comprobanteRepository.findById(pago.getComprobante().getId());

        if(!cbte.isPresent()){
            throw new BusinessException("El comprobante informado no existe en la base de datos");
        }

        if(!cbte.get().getSocio().getId().equals(pago.getSocio().getId())){
            throw new BusinessException("El comprobante nro " + cbte.get().getNroComprobante()
                    + " no pertecene al socio " + socio.get().getId());
        }

        pago.setUsuarioModifica(CurrentUser.getInstance());

        socioRepository.save(socio.get());
        return pagoRepository.save(pago);

    }

}
