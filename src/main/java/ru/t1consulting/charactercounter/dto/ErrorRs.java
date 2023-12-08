package ru.t1consulting.charactercounter.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "Common error response")
@NoArgsConstructor
public class ErrorRs {

    @Schema(description = "name of error", example = "BadRequestException")
    private String error;
    @Schema(description = "error time in timestamp", example = "12432857239")
    private Long timestamp;
    @Schema(description = "description of error", example = "Данный формат файла не поддерживается")
    private String errorDescription;

    public ErrorRs(RuntimeException exception) {
        this.error = exception.getClass().getSimpleName();
        this.errorDescription = exception.getLocalizedMessage();
        this.timestamp = new Date().getTime();
    }
}
