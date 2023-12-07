package ru.t1consulting.charctercounter;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;


@OpenAPIDefinition(
        info = @Info(
                title = "Character counter API",
                description = "API counting characters in given input", version = "1.0.0",
                contact = @Contact(
                        name = "Artem A",
                        url = "https://github.com/aldungarov-ar/character-counter.git",
                        email = "aldungarov.ar@gmail.com"
                )
        )
)
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ConfigurationPropertiesScan
@EnableScheduling
@Configuration
@EnableAspectJAutoProxy
@Slf4j
@RequiredArgsConstructor
public class CharacterCounterApp {

    public static void main(String[] args) {
        SpringApplication.run(CharacterCounterApp.class, args);
    }
}
