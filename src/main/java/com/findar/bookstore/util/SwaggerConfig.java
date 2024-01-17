package com.findar.bookstore.util;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration


@SecurityScheme(
        name="bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in= SecuritySchemeIn.HEADER
)
@OpenAPIDefinition(
        servers = {
                @Server(description= "Local ENV",
                        url = "http://localhost:8080")
        },
        security = {@SecurityRequirement(name="bearerAuth")}
)


public class SwaggerConfig {


    private String version = "1.0.0";

    @Bean
    public OpenAPI api(){
        return new OpenAPI()
                .info(new Info()
                        .contact(new Contact()
                                .email("aadebisi119@gmail.com")
                                .name("Adebisi Adeyemi"))
                        .title("Book management")
                        .description("Api that provides crud operations for book management")
                        .version(version));
    }


    @Bean
    public GroupedOpenApi usersEndpoint(){
        return  GroupedOpenApi
                .builder()
                .group("Register Admin")
                .pathsToMatch("/api/v1/admin/auth/**").build();
    }

    @Bean
    public GroupedOpenApi postEndpoint(){
        return  GroupedOpenApi
                .builder()
                .group("User Auth")
                .pathsToMatch("/api/v1/user/auth/**").build();
    }

    @Bean
    public GroupedOpenApi commentEndpoint(){
        return  GroupedOpenApi
                .builder()
                .group("Admin book management")
                .pathsToMatch("/api/v1/admin/management/**").build();
    }

    @Bean
    public GroupedOpenApi adminEndpoint(){
        return GroupedOpenApi.builder()
                .group("User book management")
                .pathsToMatch("/api/v1/user/book-usage/**").build();
    }
}
