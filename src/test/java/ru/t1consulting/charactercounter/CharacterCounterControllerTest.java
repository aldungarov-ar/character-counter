package ru.t1consulting.charactercounter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.t1consulting.charactercounter.config.GeneralProperties;
import ru.t1consulting.charactercounter.dto.CommonRs;
import ru.t1consulting.charactercounter.dto.ErrorRs;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CharacterCounterControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private GeneralProperties generalProperties;

    ObjectMapper objectMapper = new ObjectMapper();



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

    @Test
    void countCharactersInString() throws Exception {
        String testString = "abcdefg123123abc !@#$%^&*()_+=-;:'\",.<>?[]{}\\|/~";
        int expectedTotalUniqueCharactersCounted = testString.length() - 6;

        Map<Character, Long> expectedCountResult = testString.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        ZonedDateTime zdt = LocalDateTime.now()
                .atZone(ZoneId.of("Etc/UTC"));
        long testTimeStamp = zdt.toInstant().toEpochMilli();

        MvcResult mvcResult = mockMvc.perform(put("/api/v1/count/string")
                        .contentType("text/plain")
                        .content(testString))
                .andExpect(status().isOk())
                .andReturn();

        String resultAsString = mvcResult.getResponse().getContentAsString();
        CommonRs resultRs = objectMapper.readValue(resultAsString, CommonRs.class);

        assert resultRs.getCountResult().equals(expectedCountResult);
        assert resultRs.getTotalUniqueCharactersCounted() == expectedTotalUniqueCharactersCounted;
        assert resultRs.getInputType().equals("plainText");
        assert (resultRs.getTimeStamp() > testTimeStamp) &&
                (resultRs.getTimeStamp() < testTimeStamp + 15000L);
    }

    @Test
    void countCharactersInputStringIsEmpty() throws Exception {

        MvcResult mvcResult = mockMvc.perform(put("/api/v1/count/string")
                        .contentType("text/plain"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String resultContentAsString = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(resultContentAsString);

        String errorRsAsString = jsonObject.get("error_response").toString();
        ErrorRs errorRs = objectMapper.readValue(errorRsAsString, ErrorRs.class);

        assert errorRs != null;
        assert errorRs.getErrorDescription() != null;
        assert !errorRs.getErrorDescription().isEmpty();
    }
}
