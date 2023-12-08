package ru.t1consulting.charactercounter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CharacterCounterControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private GeneralProperties generalProperties;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final TestUtils testUtils = new TestUtils();

    @Test
    void checkHealthStatus() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/api/v1/health"))
                .andExpect(status().isOk())
                .andReturn();

        assert mvcResult.getResponse().getContentAsString().equals("\"status\": \"UP\"");
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
        ErrorRs errorRs = objectMapper.readValue(resultContentAsString, ErrorRs.class);

        assert errorRs != null;
        assert errorRs.getErrorDescription() != null;
        assert !errorRs.getErrorDescription().isEmpty();
    }

    @Test
    void countCharactersInputStringIsTooLong() throws Exception {
        String hugeString = testUtils.generateHugeString(generalProperties.getMaxPlainStringLength());

        MvcResult mvcResult = mockMvc.perform(put("/api/v1/count/string")
                        .contentType("text/plain")
                        .content(hugeString))
                .andExpect(status().isBadRequest())
                .andReturn();

        String resultContentAsString = mvcResult.getResponse().getContentAsString();
        ErrorRs errorRs = objectMapper.readValue(resultContentAsString, ErrorRs.class);

        assert errorRs != null;
        assert errorRs.getErrorDescription() != null;
        assert !errorRs.getErrorDescription().isEmpty();
    }

    @Test
    void countCharactersInFile() throws Exception {
        String testString = "abcdefg123123abc !@#$%^&*()_+=-;:'\",.<>?[]{}\\|/~";
        MockMultipartFile file = new MockMultipartFile("file", "testFile.txt",
                MediaType.TEXT_PLAIN_VALUE, testString.getBytes());

        int expectedTotalUniqueCharactersCounted = testString.length() - 6;

        Map<Character, Long> expectedCountResult = testString.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        ZonedDateTime zdt = LocalDateTime.now()
                .atZone(ZoneId.of("Etc/UTC"));
        long testTimeStamp = zdt.toInstant().toEpochMilli();

        MvcResult mvcResult = mockMvc.perform(multipart(HttpMethod.PUT, "/api/v1/count/file")
                        .file(file)
                        .contentType("multipart/form-data"))
                .andExpect(status().isOk())
                .andReturn();

        String resultAsString = mvcResult.getResponse().getContentAsString();
        CommonRs resultRs = objectMapper.readValue(resultAsString, CommonRs.class);

        assert resultRs.getCountResult().equals(expectedCountResult);
        assert resultRs.getTotalUniqueCharactersCounted() == expectedTotalUniqueCharactersCounted;
        assert resultRs.getInputType().equals(".txt");
        assert (resultRs.getTimeStamp() > testTimeStamp) &&
                (resultRs.getTimeStamp() < testTimeStamp + 15000L);
    }

    @Test
    void countCharactersInputFileIsTooBig() throws Exception {

        String hugeString = testUtils.generateHugeString(generalProperties.getMaxFileSizeBytes());

        MockMultipartFile file = new MockMultipartFile("file", "testFile.txt",
                MediaType.TEXT_PLAIN_VALUE, hugeString.getBytes());

        MvcResult mvcResult = mockMvc.perform(multipart(HttpMethod.PUT, "/api/v1/count/file")
                        .file(file)
                        .contentType("multipart/form-data"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String resultContentAsString = mvcResult.getResponse().getContentAsString();
        ErrorRs errorRs = objectMapper.readValue(resultContentAsString, ErrorRs.class);

        assert errorRs != null;
        assert errorRs.getErrorDescription() != null;
        assert !errorRs.getErrorDescription().isEmpty();
    }

    @Test
    void countCharactersInputFileIsEmpty() throws Exception {
        String emptyString = "";

        MockMultipartFile file = new MockMultipartFile("file", "testFile.txt",
                MediaType.TEXT_PLAIN_VALUE, emptyString.getBytes());

        MvcResult mvcResult = mockMvc.perform(multipart(HttpMethod.PUT, "/api/v1/count/file")
                        .file(file)
                        .contentType("multipart/form-data"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String resultContentAsString = mvcResult.getResponse().getContentAsString();
        ErrorRs errorRs = objectMapper.readValue(resultContentAsString, ErrorRs.class);

        assert errorRs != null;
        assert errorRs.getErrorDescription() != null;
        assert !errorRs.getErrorDescription().isEmpty();
    }

    @Test
    void countCharactersInputFileWrongExtension() throws Exception {

        String testString = "abcdefg123123abc !@#$%^&*()_+=-;:'\",.<>?[]{}\\|/~";
        MockMultipartFile file = new MockMultipartFile("file", "testFile.abra-kadabra!",
                MediaType.TEXT_PLAIN_VALUE, testString.getBytes());

        MvcResult mvcResult = mockMvc.perform(multipart(HttpMethod.PUT, "/api/v1/count/file")
                        .file(file)
                        .contentType("multipart/form-data"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String resultContentAsString = mvcResult.getResponse().getContentAsString();
        ErrorRs errorRs = objectMapper.readValue(resultContentAsString, ErrorRs.class);

        assert errorRs != null;
        assert errorRs.getErrorDescription() != null;
        assert !errorRs.getErrorDescription().isEmpty();
    }

    @Test
    void countCharactersInputFileNoExtension() throws Exception {

        String testString = "abcdefg123123abc !@#$%^&*()_+=-;:'\",.<>?[]{}\\|/~";
        MockMultipartFile file = new MockMultipartFile("file", "testFile",
                MediaType.TEXT_PLAIN_VALUE, testString.getBytes());

        MvcResult mvcResult = mockMvc.perform(multipart(HttpMethod.PUT, "/api/v1/count/file")
                        .file(file)
                        .contentType("multipart/form-data"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String resultContentAsString = mvcResult.getResponse().getContentAsString();
        ErrorRs errorRs = objectMapper.readValue(resultContentAsString, ErrorRs.class);

        assert errorRs != null;
        assert errorRs.getErrorDescription() != null;
        assert !errorRs.getErrorDescription().isEmpty();
    }
}