package ru.t1consulting.charactercounter.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Base response for /rules and exceptions")
@NoArgsConstructor
public class RulesResponse {

    private ErrorRs errorResponse;
    @Schema(description = "Maximal string length", example = "10000")
    private int maxStringLength;
    @Schema(description = "Maximal file size in bytes", example = "128")
    private int maxFileSizeBytes;
    @Schema(description = "List of allowed file types", example = "[Plain text String, .txt]")
    private String allowedInputs;
}
