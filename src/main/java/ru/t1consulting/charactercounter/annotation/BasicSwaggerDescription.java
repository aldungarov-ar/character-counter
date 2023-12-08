package ru.t1consulting.charactercounter.annotation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.core.annotation.AliasFor;
import ru.t1consulting.charactercounter.dto.ErrorRs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponse(responseCode = "400",
        content = @Content(schema = @Schema(implementation = ErrorRs.class)))
@ApiResponse(responseCode = "200")
@Operation
public @interface BasicSwaggerDescription {

    @AliasFor(annotation = Operation.class, attribute = "summary")
    String summary() default "";
}
