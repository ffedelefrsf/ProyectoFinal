/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft;

import com.funesoft.dto.PagoDTO;
import com.funesoft.dto.SocioDTO;
import com.funesoft.model.Comprobante;
import com.funesoft.model.Enfermedad;
import com.funesoft.model.Estado;
import com.funesoft.model.Localidad;
import com.funesoft.model.ObraSocial;
import com.funesoft.model.Pago;
import com.funesoft.model.Socio;
import com.funesoft.model.Tarifa;
import com.funesoft.model.Zona;
import com.funesoft.repository.ComprobanteRepository;
import com.funesoft.repository.PagoRepository;
import com.funesoft.repository.SocioRepository;
import com.funesoft.rest.PagoREST;
import com.funesoft.utilities.CurrentUser;
import com.funesoft.utilities.JsonHelper;
import com.ibm.icu.util.Calendar;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 *
 * @author faust
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class PagoTest {
    
    private MockMvc mockMvc;
    
    @MockBean
    private PagoRepository pagoRepository;

    @MockBean
    private SocioRepository socioRepository;

    @MockBean
    private ComprobanteRepository comprobanteRepository;
    
    @Autowired
    private PagoREST pagoREST;
    
    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(pagoREST).build();
    }
    
    @Test
    @DisplayName ("TEST informar pago")
    public void informarPagoTest() throws Exception {
        
        final Enfermedad enfermedad = new Enfermedad();
        enfermedad.setId(1);
        
        final Calendar calendar = Calendar.getInstance();
        calendar.set(1997, 5, 6);
        
        final SocioDTO socioDTO = new SocioDTO();
        socioDTO.setId(1);
        socioDTO.setDni(40053701);
        socioDTO.setApellido("Fedele");
        socioDTO.setNombre("Fausto");
        socioDTO.setDireccion("Candido Pujato 2754");
        socioDTO.setTelefono("3404516973");
        socioDTO.setEmail("faustofedele2013@gmail.com");
        socioDTO.setSexo("Masculino");
        socioDTO.setFechaNacimiento(calendar.getTime());
        socioDTO.setUsuarioAlta(1);
        socioDTO.setSaldo(1253d);
        socioDTO.setAdherentesAltaDTO(new ArrayList());
        final Socio socio = new Socio(socioDTO);
        socio.setId(socioDTO.getId());
        socio.setTarifa(new Tarifa());
        socio.setZona(new Zona());
        socio.setLocalidad(new Localidad());
        socio.setObraSocial(new ObraSocial());
        socio.setEnfermedad(new Enfermedad());
        socio.setUsuarioModifica(CurrentUser.getInstance());
        socio.setEstado(new Estado());
        socio.setFechaCobertura(new Date());
        
        final PagoDTO pagoDTO = new PagoDTO(150.5, 1, 2, 3);
        final Pago pago = new Pago(pagoDTO);
        pago.setUsuarioModifica(CurrentUser.getInstance());
        
        final Optional<Socio> optional = Optional.of(socio);
        
        when(socioRepository.findById(socio.getId())).thenReturn(optional);
        
        socio.setSaldo(socio.getSaldo() + pago.getValor());
        
        final Comprobante comprobante = new Comprobante(BigInteger.valueOf(1L), 200D, CurrentUser.getInstance(), socio, false);
        final Integer idComprobante = pago.getComprobante().getId();
        comprobante.setId(idComprobante);
        Optional<Comprobante> cbte = Optional.of(comprobante);
        
        when(comprobanteRepository.findById(idComprobante)).thenReturn(cbte);
        when(socioRepository.save(socio)).thenReturn(socio);
        when(pagoRepository.save(pago)).thenReturn(pago);
        
        final String requestBody = JsonHelper.objectToString(pagoDTO);
        
        // SUCCESS
        mockMvc.perform(post("/pago/informarPago")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true));
        
    }
    
}
