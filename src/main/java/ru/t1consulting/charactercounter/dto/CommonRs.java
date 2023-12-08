package ru.t1consulting.charactercounter.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "Basic response with counting results")
@NoArgsConstructor
public class CommonRs {
    @Schema(example = "12432857239")
    private Long timeStamp;
    @Schema(example = "Map of Characters and counts")
    private Map<Character, Long> countResult;
    @Schema(example = "200")
    private Integer totalUniqueCharactersCounted;
    @Schema(example = ".txt")
    private String inputType;

    public CommonRs(Map<Character, Long> countResult, String inputType) {
        this.countResult = countResult;
        this.inputType = inputType;
        this.totalUniqueCharactersCounted = countResult.keySet().size();
        ZonedDateTime zdt = LocalDateTime.now()
                .atZone(ZoneId.of("Etc/UTC"));
        this.timeStamp = zdt.toInstant().toEpochMilli();
    }
}
