package com.vivek.cicd.config;



import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI taskManagementOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Task Management API")
                        .description(
                                "REST API for Employee Task Management System"
                        )
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Vivek Pandey")
                                .email("vivek@example.com")
                        ));
    }
}