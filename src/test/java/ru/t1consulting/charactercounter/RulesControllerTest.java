package ru.t1consulting.charactercounter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.t1consulting.charactercounter.config.GeneralProperties;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RulesControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private GeneralProperties generalProperties;

    @Test
    void getRules() throws Exception {
        mockMvc.perform(get("/api/v1/rules"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.max_string_length")
                        .value(generalProperties.getMaxPlainStringLength()))
                .andExpect(jsonPath("$.max_file_size_bytes")
                        .value(generalProperties.getMaxFileSizeBytes()))
                .andExpect(jsonPath("$.allowed_inputs")
                        .value(generalProperties.getTypesAllowed().toString()));
    }
}
