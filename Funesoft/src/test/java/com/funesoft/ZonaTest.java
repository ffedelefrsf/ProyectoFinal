package com.funesoft;

import com.funesoft.dto.CobradorDTO;
import com.funesoft.dto.ZonaCobradoresDTO;
import com.funesoft.dto.ZonaDTO;
import com.funesoft.model.Cobrador;
import com.funesoft.model.Zona;
import com.funesoft.model.ZonaCobrador;
import com.funesoft.repository.ZonaCobradorRepository;
import com.funesoft.repository.ZonaRepository;
import com.funesoft.rest.ZonaREST;
import com.funesoft.utilities.CurrentUser;
import com.funesoft.utilities.JsonHelper;
import com.ibm.icu.util.Calendar;
import java.util.ArrayList;
import java.util.Date;
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
    
    @MockBean
    private ZonaCobradorRepository zonaCobradorRepository;
    
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
    
    @Test
    @DisplayName ("TEST Insert Zona")
    public void insertZonasTest() throws Exception {
        
        final Zona zona = new Zona(new ZonaDTO(1, 1, "My zona"));
        final List<Cobrador> cobradores = new ArrayList();
        final Calendar calendar = Calendar.getInstance();
        calendar.set(1997, 5, 6);
        final Cobrador cobrador = new Cobrador(new CobradorDTO(1, 40053701, "Fedele", "Fausto", "Avellaneda 2178", "3404516973", "faustofedele2013@gmail.com", "Masculino", calendar.getTime(), new Date(), null, 1));
        cobradores.add(cobrador);
        
        final ZonaCobradoresDTO zonaCobradoresDTO = new ZonaCobradoresDTO();
        zonaCobradoresDTO.setCobradores(cobradores);
        zonaCobradoresDTO.setZona(zona);
        final String requestBody = JsonHelper.objectToString(zonaCobradoresDTO);
        
        // SUCCESS
        zona.setUsuarioModifica(CurrentUser.getInstance());
        when(zonaRepository.save(zonaCobradoresDTO.getZona())).thenReturn(zona);
        ZonaCobrador zonaCobrador = new ZonaCobrador();
        zonaCobrador.setZona(zonaCobradoresDTO.getZona());
        zonaCobrador.setCobrador(cobrador);
        zonaCobrador.setUsuarioModifica(CurrentUser.getInstance());
        when(zonaCobradorRepository.save(zonaCobrador)).thenReturn(zonaCobrador);
        
        mockMvc.perform(post("/zona/insert")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true));
        
        // FAILED EMPTY REQUEST
        mockMvc.perform(post("/zona/insert")
            .contentType(MediaType.APPLICATION_JSON)
            .content("")
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
        
    }
    
}
