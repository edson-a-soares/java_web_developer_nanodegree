package com.udacity.vehicles.infrastructure.docs;

import java.util.Collections;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ApiInfo;
import org.springframework.context.annotation.Bean;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class Swagger {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(regex("/cars.*"))
            .build()
            .useDefaultResponseMessages(false);
    }

    private ApiInfo metadata() {
        return new ApiInfo(
            "Vehicles Website API",
            "This API provides Vehicles information.",
            "v0.1",
            "http://www.udacity.com/tos",
            new Contact("Edson A. Soares", "www.udacity.com", "myeaddress@e-mail.com"),
            "License",
            "https://github.com/edson-a-soares/java_developer_nanodegree/tree/main/microservices/LISCENSE", Collections.emptyList()
        );
    }
}