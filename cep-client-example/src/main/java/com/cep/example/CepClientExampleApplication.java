package com.cep.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                "logic.components.cep.example",
                "logic.components.cep.client",
                "logic.components.oauth2.component",
                "logic.components.oauth2.config"
        }
)
@EnableAutoConfiguration
public class CepClientExampleApplication {

    public static void main(String[] args) {

        SpringApplication.run(CepClientExampleApplication.class, args);
    }

}
