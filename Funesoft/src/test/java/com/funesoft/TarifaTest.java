/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft;

import com.funesoft.dto.TarifaDTO;
import com.funesoft.model.Plan;
import com.funesoft.model.Tarifa;
import com.funesoft.model.Usuario;
import com.funesoft.repository.TarifaRepository;
import com.funesoft.rest.TarifaREST;
import com.funesoft.utilities.JsonHelper;
import java.util.ArrayList;
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
public class TarifaTest {
    
    private MockMvc mockMvc;
    
    @MockBean
    private TarifaRepository tarifaRepository;
    
    @Autowired
    private TarifaREST tarifaREST;
    
    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(tarifaREST).build();
    }
    
    @Test
    @DisplayName ("TEST Update Tarifa")
    public void updateTarifaTest() throws Exception {
        
        final TarifaDTO tarifaDTO = new TarifaDTO();
        tarifaDTO.setId(1);
        tarifaDTO.setDescripcion("asd");
        tarifaDTO.setNroTarifa(1);
        tarifaDTO.setValor(190f);
        tarifaDTO.setPlan(new Plan());
        tarifaDTO.setListRango(new ArrayList());
        final Tarifa tarifa = new Tarifa(tarifaDTO);
        tarifa.setUsuarioModifica(new Usuario());
        
        final String requestBody = JsonHelper.objectToString(tarifa);
        
        final Optional<Tarifa> toReturn = Optional.of(new Tarifa());
        
        // SUCCESS
        when(tarifaRepository.findById(1)).thenReturn(toReturn);
        mockMvc.perform(post("/tarifa/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true));
        
        // FAILED EMPTY REQUEST
//        mockMvc.perform(post("/zona/getAll")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content("")
//            .accept(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(status().isBadRequest());
        
    }
}
