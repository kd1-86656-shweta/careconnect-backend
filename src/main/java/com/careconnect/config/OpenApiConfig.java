package com.careconnect.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI careConnectOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CareConnect Backend API")
                        .description("Backend APIs for CareConnect platform")
                        .version("v1.0"));
    }
}

