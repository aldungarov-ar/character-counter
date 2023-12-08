package ru.t1consulting.charactercounter.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.t1consulting.charactercounter.dto.ErrorRs;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ErrorRs> handleCommonExceptions(BadRequestException exception) {
        ErrorRs errorRs = new ErrorRs(exception);

        return ResponseEntity.badRequest().body(errorRs);
    }
}