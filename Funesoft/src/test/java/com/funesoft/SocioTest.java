/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.funesoft.controller.CoberturaController;
import com.funesoft.dto.SocioDTO;
import com.funesoft.model.CoberturaEdad;
import com.funesoft.model.CoberturaEnfermedad;
import com.funesoft.model.CoberturaPeso;
import com.funesoft.model.Enfermedad;
import com.funesoft.model.Estado;
import com.funesoft.model.Localidad;
import com.funesoft.model.ObraSocial;
import com.funesoft.model.Socio;
import com.funesoft.model.Tarifa;
import com.funesoft.model.Zona;
import com.funesoft.repository.CoberturaEdadRepository;
import com.funesoft.repository.CoberturaEnfermedadRepository;
import com.funesoft.repository.CoberturaPesoRepository;
import com.funesoft.repository.EnfermedadRepository;
import com.funesoft.repository.EstadoRepository;
import com.funesoft.repository.SocioRepository;
import com.funesoft.rest.SocioREST;
import com.funesoft.utilities.CualidadCoberturaEnum;
import com.funesoft.utilities.CurrentUser;
import com.funesoft.utilities.EstadoEnum;
import com.funesoft.utilities.JsonHelper;
import com.ibm.icu.util.Calendar;
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
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
public class SocioTest {
    
    private MockMvc mockMvc;
    
    @MockBean
    private SocioRepository socioRepository;
    
    @MockBean
    private CoberturaPesoRepository coberturaPesoRepository;
    
    @MockBean
    private CoberturaEdadRepository coberturaEdadRepository;
    
    @MockBean
    private CoberturaEnfermedadRepository coberturaEnfermedadRepository;
    
    @MockBean
    private EnfermedadRepository enfermedadRepository;
    
    @MockBean
    private EstadoRepository estadoRepository;
    
    @Autowired
    private SocioREST socioREST;
    
    @Autowired
    private CoberturaController coberturaController;
    
    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(socioREST).build();
    }
    
    @Test
    @DisplayName ("TEST Get All Socios")
    public void getAllSociosTest() throws Exception {
        
        final List<Socio> toReturn = new ArrayList();
        toReturn.add(new Socio());
        
        final String requestBody = JsonHelper.objectToString(new Socio());
        
        // SUCCESS
        when(socioRepository.findAll(Example.of(new Socio()))).thenReturn(toReturn);
        mockMvc.perform(post("/socio/get")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true));
        
        // FAILED EMPTY REQUEST
        mockMvc.perform(post("/socio/get")
            .contentType(MediaType.APPLICATION_JSON)
            .content("")
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
        
    }
    
    @Test
    @DisplayName ("TEST Get Socio")
    public void getSocioTest() throws Exception {
        
        final ObjectMapper objectMapper = new ObjectMapper();
        final List<Socio> toReturn = new ArrayList();
        
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
        toReturn.add(socio);
        
        final String requestBody = JsonHelper.objectToString(socio);
        
        final List responseList = new ArrayList();
        responseList.add(objectMapper.convertValue(socio, Map.class));
        // SUCCESS
        when(socioRepository.findAll(Example.of(socio))).thenReturn(toReturn);
        mockMvc.perform(post("/socio/get")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data").value(responseList));
        
        // FAILED EMPTY REQUEST
        mockMvc.perform(post("/socio/get")
            .contentType(MediaType.APPLICATION_JSON)
            .content("")
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
        
    }
    
    @Test
    @DisplayName ("TEST Get All DNIs")
    public void getAllDNIsTest() throws Exception {
        
        final List<String> toReturn = new ArrayList();
        toReturn.add("17997379");
        toReturn.add("40053701");
        
        final String requestBody = "";
        
        // SUCCESS
        when(socioRepository.findDniByOrderByDniDesc()).thenReturn(toReturn);
        mockMvc.perform(get("/socio/getDNIs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data").value(toReturn));
        
    }
    
    @Test
    @DisplayName ("TEST Update Socio")
    public void updateSocioTest() throws Exception {
        
        final ObjectMapper objectMapper = new ObjectMapper();
        final List<Socio> toReturn = new ArrayList();
        
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
        toReturn.add(socio);
        
        final String requestBody = JsonHelper.objectToString(socio);
        
        final Optional<Socio> optional = Optional.of(new Socio());
        
        // FAILED IF IT DOESN'T EXISTS
        mockMvc.perform(post("/socio/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(false));
        
        // FAILED EMPTY REQUEST
        mockMvc.perform(post("/socio/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content("")
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
        
        // SUCCESS
        when(socioRepository.findById(socio.getId())).thenReturn(optional);
        when(socioRepository.save(socio)).thenReturn(socio);
        mockMvc.perform(post("/socio/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data").value(objectMapper.convertValue(socio, Map.class)));
        
    }
    
    @Test
    @DisplayName ("TEST Insert Socio")
    public void insertSocioTest() throws Exception {
        
        final ObjectMapper objectMapper = new ObjectMapper();
        
        final Calendar calendar = Calendar.getInstance();
        calendar.set(1997, 5, 6);
        
        final Socio socioTest = new Socio();
        socioTest.setFechaNacimiento(calendar.getTime());
        final Short edad = socioTest.getEdad();
        
        final Enfermedad enfermedad = new Enfermedad();
        enfermedad.setId(1);
        
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
        socioDTO.setIdTarifa(1);
        socioDTO.setIdZona(1);
        socioDTO.setIdLocalidad(1);
        socioDTO.setIdObraSocial(1);
        socioDTO.setIdEnfermedad(enfermedad.getId());
        socioDTO.setAdherentesAltaDTO(new ArrayList());
        
        String requestBody = JsonHelper.objectToString(socioDTO);
        
        final Socio socio = new Socio(socioDTO);
        
        final float indicadorEnfermedad = 88f;
        final float indicadorEdad = 21f;
        final float pesoEnfermedad = 9f;
        final float pesoEdad = 10f;
        
        final CoberturaEnfermedad coberturaEnfermedad = new CoberturaEnfermedad();
        coberturaEnfermedad.setIndicador(indicadorEnfermedad);
        
        final CoberturaEdad coberturaEdad = new CoberturaEdad();
        coberturaEdad.setIndicador(indicadorEdad);
        
        final CoberturaPeso coberturaPesoEnfermedad = new CoberturaPeso();
        coberturaPesoEnfermedad.setPeso(pesoEnfermedad);
        
        final CoberturaPeso coberturaPesoEdad = new CoberturaPeso();
        coberturaPesoEdad.setPeso(pesoEdad);
        
        final Estado estado = new Estado();
        socio.setUsuarioModifica(CurrentUser.getInstance());
        
        // SUCCESS
        when(coberturaEnfermedadRepository.findByEnfermedad(enfermedad)).thenReturn(coberturaEnfermedad);
        when(coberturaEdadRepository.findByRangoEdad(edad)).thenReturn(coberturaEdad);
        when(coberturaPesoRepository.findByDescripcion(CualidadCoberturaEnum.ENFERMEDAD.getDescripcion())).thenReturn(coberturaPesoEnfermedad);
        when(coberturaPesoRepository.findByDescripcion(CualidadCoberturaEnum.EDAD.getDescripcion())).thenReturn(coberturaPesoEdad);
        
        socio.setFechaCobertura(coberturaController.calculoCobertura(socio));
        
        when(estadoRepository.findByNroEstado(EstadoEnum.ALTA.getCodigo())).thenReturn(estado);
        socio.setEstado(estado);
        
        when(socioRepository.save(socio)).thenReturn(socio);
        
        mockMvc.perform(post("/socio/insert")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.message").isEmpty())
            .andExpect(jsonPath("$.errores").isEmpty())
            .andExpect(jsonPath("$.data").value(objectMapper.convertValue(socio, Map.class)));
        
        // FAILED ENFERMEDAD DOESN'T EXIST
        socioDTO.setIdEnfermedad(-1);
        enfermedad.setId(-1);
        when(coberturaEnfermedadRepository.findByEnfermedad(enfermedad)).thenReturn(null);
        requestBody = JsonHelper.objectToString(socioDTO);
        mockMvc.perform(post("/socio/insert")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(false));
        
    }
}
