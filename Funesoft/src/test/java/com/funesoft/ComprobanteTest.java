/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft;

import com.funesoft.controller.TarifaController;
import com.funesoft.dto.PlanDTO;
import com.funesoft.dto.RangoTarifaDTO;
import com.funesoft.dto.SocioDTO;
import com.funesoft.dto.TarifaDTO;
import com.funesoft.model.Adherente;
import com.funesoft.model.Comprobante;
import com.funesoft.model.Enfermedad;
import com.funesoft.model.Estado;
import com.funesoft.model.Localidad;
import com.funesoft.model.ObraSocial;
import com.funesoft.model.Plan;
import com.funesoft.model.RangoTarifa;
import com.funesoft.model.Socio;
import com.funesoft.model.Tarifa;
import com.funesoft.model.Usuario;
import com.funesoft.model.Zona;
import com.funesoft.repository.AdherenteRepository;
import com.funesoft.repository.ComprobanteRepository;
import com.funesoft.repository.RangoTarifaRepository;
import com.funesoft.repository.SocioRepository;
import com.funesoft.repository.TarifaRepository;
import com.funesoft.rest.ComprobanteREST;
import com.funesoft.rest.TarifaREST;
import com.funesoft.utilities.CurrentUser;
import com.funesoft.utilities.JsonHelper;
import com.ibm.icu.util.Calendar;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
public class ComprobanteTest {
    
    private MockMvc mockMvc;
    
    @MockBean
    private SocioRepository socioRepository;
    
    @MockBean
    private ComprobanteRepository comprobanteRepository;
    
    @MockBean
    private RangoTarifaRepository rangoTarifaRepository;
    
    @MockBean
    private AdherenteRepository adherenteRepository;
    
    @Autowired
    private TarifaController tarifaController;
    
    @Autowired
    private ComprobanteREST comprobanteREST;
    
    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(comprobanteREST).build();
    }
    
    @Test
    @DisplayName ("TEST Generar comprobantes masivos")
    public void generarComprobantesMasivosTest() throws Exception {
        
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
        socio.setUsuarioModifica(CurrentUser.getInstance());
        final TarifaDTO tarifaDTO = new TarifaDTO();
        tarifaDTO.setId(1);
        tarifaDTO.setDescripcion("asd");
        tarifaDTO.setNroTarifa(1);
        tarifaDTO.setValor(190f);
        final PlanDTO planDTO = new PlanDTO();
        planDTO.setId(1);
        planDTO.setNroPlan(1);
        planDTO.setDescripcion(Plan.INDIVIDUAL);
        Plan plan = new Plan(planDTO);
        tarifaDTO.setPlan(plan);
        final Tarifa tarifa = new Tarifa(tarifaDTO);
        socio.setTarifa(tarifa);
        
        final List<Socio> sociosList = new ArrayList();
        sociosList.add(socio);
        final Comprobante comprobante = new Comprobante(BigInteger.valueOf(1L), 200D, CurrentUser.getInstance(), socio, false);
        Optional<Comprobante> ultimoCbte =  Optional.of(comprobante);
        
        when(comprobanteRepository.findUltimoComprobante()).thenReturn(ultimoCbte);
        
        final List<RangoTarifa> rangosTarifa = new ArrayList();
        rangosTarifa.add(new RangoTarifa(new RangoTarifaDTO(1, 150, 100D, null), tarifa, null));
        System.out.println("rangsTarifa aux " + rangosTarifa);
        when(rangoTarifaRepository.findByTarifa(tarifa)).thenReturn(rangosTarifa);
        
        Optional<Socio> optionalSocio = Optional.of(socio);
        when(socioRepository.findById(socio.getId())).thenReturn(optionalSocio);
        Map<Integer, Float> map = tarifaController.getValorTarifaByAsociado(socio);
        
        Optional<Adherente> optAdherente = Optional.ofNullable(null);
        when(adherenteRepository.findByDni(((Map.Entry<Integer, Float>)map.entrySet().toArray()[0]).getKey())).thenReturn(optAdherente);
        
        socio.setSaldo(socio.getSaldo() - map.get(0).doubleValue());
        when(socioRepository.save(socio)).thenReturn(socio);
        
        
        Double valorCbte = map.get(0).doubleValue(); // - socio.getSaldo();
        BigInteger nroComprobante = comprobante.getNroComprobante().add(BigInteger.ONE);
        
        final Comprobante newComprobante = new Comprobante(nroComprobante, valorCbte < 0D ? 0D : valorCbte, CurrentUser.getInstance(), socio, false);
        when(comprobanteRepository.save(newComprobante)).thenReturn(newComprobante);
        
        
        
        when(socioRepository.findAllActivo()).thenReturn(sociosList);
        
        // SUCCESS
        mockMvc.perform(post("/comprobante/generarComprobantesMasivos")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true));
        
    }
    
}
