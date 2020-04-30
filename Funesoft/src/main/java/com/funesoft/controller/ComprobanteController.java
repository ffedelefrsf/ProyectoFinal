package com.funesoft.controller;

import com.funesoft.dto.SocioDTO;
import com.funesoft.model.Comprobante;
import com.funesoft.model.ParametroEmpresa;
import com.funesoft.model.Socio;
import com.funesoft.model.Usuario;
import com.funesoft.repository.AdherenteRepository;
import com.funesoft.repository.ComprobanteRepository;
import com.funesoft.repository.ParametroEmpresaRepository;
import com.funesoft.repository.SocioRepository;
import com.funesoft.utilities.BusinessException;
import com.funesoft.utilities.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;

import java.math.BigInteger;
import java.sql.Date;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@Controller
public class ComprobanteController {

    @Autowired
    private ComprobanteRepository comprobanteRepository;

    @Autowired
    private SocioRepository socioRepository;

    @Autowired
    private TarifaController tarifaController;

    @Autowired
    private ParametroEmpresaRepository parametroEmpresaRepository;

    public Comprobante generarComprobante(final Integer idSocio) throws BusinessException {

        //BUSCO LA EL VALOR QUE PAGARÍA EL SOCIO Y SUS ADHERENTES A TRAVÉS DE LA TARIFA

        Optional<Socio> socio = socioRepository.findById(idSocio);

        if (!socio.isPresent()) {
            throw new BusinessException("El socio informado no existe");
        }

        Map<Integer, Float> map = tarifaController.getValorTarifaByAsociado(socio.get());

        //RECUPERO LOS PARÁMETROS DE LA EMPRESA
        Optional<ParametroEmpresa> parametro = parametroEmpresaRepository.findById(1);

        if(parametro.isPresent()){
            throw new BusinessException("No existen parametros para la empresa informados");
        }

        return new Comprobante(
                comprobanteRepository.ultimoNroComprobante().add(BigInteger.valueOf(1)),
                map.get(0).doubleValue(),
                parametro.get(),
                CurrentUser.getInstance()
        );

    }


}
