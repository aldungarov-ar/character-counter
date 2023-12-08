package ru.t1consulting.charactercounter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.t1consulting.charactercounter.annotation.Info;
import ru.t1consulting.charactercounter.dto.CommonRs;
import ru.t1consulting.charactercounter.dto.RulesResponse;
import ru.t1consulting.charactercounter.service.CountService;
import ru.t1consulting.charactercounter.service.RulesService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Info
public class CharacterCounterController {

    private final RulesService rulesService;
    private final CountService countService;

    @GetMapping("/health")
    public String checkHealth() {

        return "\"status\": \"UP\"";
    }

    @GetMapping("/rules")
    public RulesResponse getRules() {

        return rulesService.getRules();
    }

    @PutMapping(value = "/count/file", produces = "application/json", consumes = "multipart/form-data")
    public CommonRs countCharactersInFile(@RequestBody(required = false) MultipartFile file) {

        return countService.countFileCharacters(file);
    }


    @PutMapping(value = "/count/string", produces = "application/json", consumes = "text/plain")
    public CommonRs countCharactersInString(@RequestBody(required = false) String plainTextRequest) {

        return countService.countPlainTextCharacters(plainTextRequest);
    }
}
