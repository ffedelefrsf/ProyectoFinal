package com.funesoft;

import com.funesoft.dto.ZonaDTO;
import com.funesoft.model.Zona;
import com.funesoft.repository.ZonaRepository;
import com.funesoft.rest.ZonaREST;
import com.funesoft.utilities.JsonHelper;
import java.util.ArrayList;
import java.util.List;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class ZonaTest {
    
    private MockMvc mockMvc;
    
    @MockBean
    private ZonaRepository zonaRepository;
    
    @Autowired
    private ZonaREST zonaREST;
    
    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(zonaREST).build();
    }
    
    @Test
    @DisplayName ("TEST Get All Zonas")
    public void getAllZonasTest() throws Exception {
        
        final List<Zona> toReturn = new ArrayList();
        toReturn.add(new Zona(new ZonaDTO(1, 1, "My zona")));
        
        final String requestBody = JsonHelper.objectToString(new Zona());
        
        // SUCCESS
        when(zonaRepository.findAll(Example.of(new Zona()))).thenReturn(toReturn);
        mockMvc.perform(post("/zona/getAll")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true));
        
        // FAILED EMPTY REQUEST
        mockMvc.perform(post("/zona/getAll")
            .contentType(MediaType.APPLICATION_JSON)
            .content("")
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
        
    }
    
}
