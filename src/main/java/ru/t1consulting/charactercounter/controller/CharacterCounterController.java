package ru.t1consulting.charactercounter.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.t1consulting.charactercounter.annotation.BasicSwaggerDescription;
import ru.t1consulting.charactercounter.annotation.Info;
import ru.t1consulting.charactercounter.dto.CommonRs;
import ru.t1consulting.charactercounter.service.CountService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Info
public class CharacterCounterController {

    private final CountService countService;

    @GetMapping("/health")
    @ApiResponse(responseCode = "200",
            content = @Content(examples = @ExampleObject(value = "\"status\": \"UP\"")))
    public String checkHealth() {

        return "\"status\": \"UP\"";
    }

    @BasicSwaggerDescription(summary = "Count all characters in given FILE")
    @PutMapping(value = "/count/file", produces = "application/json", consumes = "multipart/form-data")
    public CommonRs countCharactersInFile(@RequestBody(required = false) MultipartFile file) {

        return countService.countFileCharacters(file);
    }


    @BasicSwaggerDescription(summary = "Count all characters in given STRING")
    @PutMapping(value = "/count/string", produces = "application/json", consumes = "text/plain")
    public CommonRs countCharactersInString(@RequestBody(required = false) String plainTextRequest) {

        return countService.countPlainTextCharacters(plainTextRequest);
    }
}
