package com.example.ecommerce.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI ecommerceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("E-Commerce API")
                        .description("API para la prueba de gestión de un eCommerce para empresa Talataa ")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Joao Maldonado")
                                .url("https://www.linkedin.com/in/joao-maldonado-a084151ba/")
                                .email("joao.maldonado96@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("ecommerce-backend GitHub")
                        .url("https://github.com/joaomaldonado96/ecommerce-backend"));
    }
}