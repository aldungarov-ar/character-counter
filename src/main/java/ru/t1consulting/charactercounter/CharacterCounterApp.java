package ru.t1consulting.charactercounter;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@OpenAPIDefinition(
        info = @Info(
                title = "Character counter API",
                description = "API counting characters in given input", version = "1.0.0",
                contact = @Contact(
                        name = "Artem Aldungarov",
                        url = "https://github.com/aldungarov-ar/character-counter.git",
                        email = "aldungarov.ar@gmail.com"
                )
        )
)
@SpringBootApplication
@ConfigurationPropertiesScan
@Configuration
@EnableAspectJAutoProxy
public class CharacterCounterApp {

    public static void main(String[] args) {
        SpringApplication.run(CharacterCounterApp.class, args);
    }
}
