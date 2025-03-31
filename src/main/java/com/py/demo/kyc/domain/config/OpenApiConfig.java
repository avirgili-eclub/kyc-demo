package com.py.demo.kyc.domain.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de KYC (Know Your Client)")
                        .description("API para la validación de identidad y prueba de vida de clientes")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Equipo de Desarrollo")
                                .email("emelgarejo@gmail.com;avirgilitech@gmail.com")));
    }
}