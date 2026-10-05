package com.ccpm.evaluacion.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI usersOpenApi() {
        return new OpenAPI().info(new Info()
                .title("API de registro de usuarios")
                .version("1.0.0")
                .description("API RESTful para la creacion y registro de usuarios. "
                        + "Todos los endpoints aceptan y retornan unicamente JSON."));
    }
}
