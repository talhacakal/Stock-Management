package com.jackal.stockmanagement.productservice.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Talha Çakal",
                        email = "muhammedtalhacakal@gmail.com",
                        url = "linkedin.com/in/talhacakal"
                ),
                description = "OpenApi documentation for Product Service",
                title = "Product Service",
                version = "1.0"
        ),
        servers = @Server(
                description = "Local Environment",
                url = "http://localhost:9788"
        )
)
public class SwaggerConfig {
}
