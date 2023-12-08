package ru.t1consulting.charactercounter.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.t1consulting.charactercounter.dto.ErrorRs;
import ru.t1consulting.charactercounter.dto.RulesResponse;
import ru.t1consulting.charactercounter.service.RulesService;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final RulesService rulesService;

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<RulesResponse> handleCommonExceptions(BadRequestException exception) {
        RulesResponse rulesResponse = rulesService.getRules();
        rulesResponse.setErrorResponse(new ErrorRs(exception));

        return ResponseEntity.badRequest().body(rulesResponse);
    }
}