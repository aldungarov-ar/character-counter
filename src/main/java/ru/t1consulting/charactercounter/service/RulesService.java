package ru.t1consulting.charactercounter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.t1consulting.charactercounter.annotation.Info;
import ru.t1consulting.charactercounter.config.GeneralProperties;
import ru.t1consulting.charactercounter.dto.RulesResponse;

@Service
@RequiredArgsConstructor
@Info
public class RulesService {
    private final GeneralProperties generalProperties;


    public RulesResponse getRules() {
        RulesResponse rulesResponse = new RulesResponse();
        rulesResponse.setAllowedInputs(generalProperties.getTypesAllowed().toString());
        rulesResponse.setMaxStringLength(generalProperties.getMaxPlainStringLength());
        rulesResponse.setMaxFileSizeBytes(generalProperties.getMaxFileSizeBytes());

        return rulesResponse;
    }
}
