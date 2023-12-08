package ru.t1consulting.charactercounter.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.t1consulting.charactercounter.annotation.Info;
import ru.t1consulting.charactercounter.dto.RulesResponse;
import ru.t1consulting.charactercounter.service.RulesService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Info
public class RulesController {

    private final RulesService rulesService;

    @GetMapping("/rules")
    @ApiResponse(responseCode = "200")
    public RulesResponse getRules() {

        return rulesService.getRules();
    }
}
