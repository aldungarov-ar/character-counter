package ru.t1consulting.charactercounter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@SuppressWarnings("unused")
@Configuration
@ConfigurationProperties(prefix = "input")
@Data
public class GeneralProperties {

    private int maxPlainStringLength;
    private List<String> typesAllowed;
    private int maxFileSizeBytes;
}
