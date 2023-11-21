package com.github.njsuarez.prices.infrastructure.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI myOpenAPI() {
        Contact contact = new Contact();

        contact.setName("njsuarez");
        contact.setUrl("https://www.github.com/njsuarez");

        Info info = new Info()
                .title("Prices API")
                .version("0.0.1")
                .contact(contact)
                .description("Prices API - Let's query for prices");

        return new OpenAPI().info(info);
    }
}
