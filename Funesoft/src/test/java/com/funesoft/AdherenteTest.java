/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.funesoft.controller.CoberturaController;
import com.funesoft.dto.AdherenteDTO;
import com.funesoft.model.Adherente;
import com.funesoft.model.CoberturaEdad;
import com.funesoft.model.CoberturaEnfermedad;
import com.funesoft.model.CoberturaPeso;
import com.funesoft.model.Enfermedad;
import com.funesoft.model.Estado;
import com.funesoft.model.Localidad;
import com.funesoft.model.ObraSocial;
import com.funesoft.model.Tarifa;
import com.funesoft.model.Zona;
import com.funesoft.repository.AdherenteRepository;
import com.funesoft.repository.CoberturaEdadRepository;
import com.funesoft.repository.CoberturaEnfermedadRepository;
import com.funesoft.repository.CoberturaPesoRepository;
import com.funesoft.repository.EstadoRepository;
import com.funesoft.repository.SocioRepository;
import com.funesoft.rest.AdherenteREST;
import com.funesoft.utilities.CualidadCoberturaEnum;
import com.funesoft.utilities.CurrentUser;
import com.funesoft.utilities.EstadoEnum;
import com.funesoft.utilities.JsonHelper;
import com.ibm.icu.util.Calendar;
import java.util.Date;
import java.util.Map;
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
public class AdherenteTest {
    
    private MockMvc mockMvc;
    
    @MockBean
    private AdherenteRepository adherenteRepository;
    
    @MockBean
    private CoberturaPesoRepository coberturaPesoRepository;
    
    @MockBean
    private CoberturaEdadRepository coberturaEdadRepository;
    
    @MockBean
    private CoberturaEnfermedadRepository coberturaEnfermedadRepository;
    
    @MockBean
    private EstadoRepository estadoRepository;
    
    @Autowired
    private CoberturaController coberturaController;
    
    @Autowired
    private AdherenteREST adherenteREST;
    
    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(adherenteREST).build();
    }
    
    @Test
    @DisplayName ("TEST Insert Adherente")
    public void getInsertAdherenteTest() throws Exception {
        
//        final ObjectMapper objectMapper = new ObjectMapper();
//        
//        final Enfermedad enfermedad = new Enfermedad();
//        enfermedad.setId(1);
//        
//        final Calendar calendar = Calendar.getInstance();
//        calendar.set(1997, 5, 6);
//        
//        final AdherenteDTO adherenteDTO = new AdherenteDTO();
//        adherenteDTO.setId(1);
//        adherenteDTO.setDni(40053701);
//        adherenteDTO.setApellido("Fedele");
//        adherenteDTO.setNombre("Fausto");
//        adherenteDTO.setDireccion("Candido Pujato 2754");
//        adherenteDTO.setTelefono("3404516973");
//        adherenteDTO.setEmail("faustofedele2013@gmail.com");
//        adherenteDTO.setSexo("Masculino");
//        adherenteDTO.setFechaNacimiento(calendar.getTime());
//        adherenteDTO.setSaldo(1253f);
//        final Adherente adherente = new Adherente(adherenteDTO);
//        adherente.setId(adherenteDTO.getId());
//        adherente.setZona(new Zona());
//        adherente.setLocalidad(new Localidad());
//        adherente.setObraSocial(new ObraSocial());
//        adherente.setEnfermedad(enfermedad);
//        adherente.setUsuarioModifica(CurrentUser.getInstance());
//        adherente.setEstado(new Estado());
//        final float indicadorEnfermedad = 88f;
//        final float indicadorEdad = 21f;
//        final float pesoEnfermedad = 9f;
//        final float pesoEdad = 10f;
//        
//        final CoberturaEnfermedad coberturaEnfermedad = new CoberturaEnfermedad();
//        coberturaEnfermedad.setIndicador(indicadorEnfermedad);
//        
//        final CoberturaEdad coberturaEdad = new CoberturaEdad();
//        coberturaEdad.setIndicador(indicadorEdad);
//        
//        final CoberturaPeso coberturaPesoEnfermedad = new CoberturaPeso();
//        coberturaPesoEnfermedad.setPeso(pesoEnfermedad);
//        
//        final CoberturaPeso coberturaPesoEdad = new CoberturaPeso();
//        coberturaPesoEdad.setPeso(pesoEdad);
//        
//        final Estado estado = new Estado();
//        adherente.setUsuarioModifica(CurrentUser.getInstance());
//        
//        // SUCCESS
//        when(coberturaEnfermedadRepository.findByEnfermedad(enfermedad)).thenReturn(coberturaEnfermedad);
//        when(coberturaEdadRepository.findByRangoEdad(adherente.getEdad())).thenReturn(coberturaEdad);
//        when(coberturaPesoRepository.findByDescripcion(CualidadCoberturaEnum.ENFERMEDAD.getDescripcion())).thenReturn(coberturaPesoEnfermedad);
//        when(coberturaPesoRepository.findByDescripcion(CualidadCoberturaEnum.EDAD.getDescripcion())).thenReturn(coberturaPesoEdad);
//        adherente.setFechaCobertura(coberturaController.calculoCobertura(adherente));
//        when(estadoRepository.findByNroEstado(EstadoEnum.ALTA.getCodigo())).thenReturn(estado);
//        
//        when(adherenteRepository.save(adherente)).thenReturn(adherente);
//        
//        final String requestBody = JsonHelper.objectToString(adherenteDTO);
//        
//        mockMvc.perform(post("/adherente/insert")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(requestBody)
//            .accept(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.success").value(true))
//            .andExpect(jsonPath("$.message").isEmpty())
//            .andExpect(jsonPath("$.errores").isEmpty())
//            .andExpect(jsonPath("$.data").value(objectMapper.convertValue(adherente, Map.class)));
        
    }
    
}
