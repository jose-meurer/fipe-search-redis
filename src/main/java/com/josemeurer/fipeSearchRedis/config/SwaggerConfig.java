package com.josemeurer.fipeSearchRedis.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("FIPE Search API - Redis Cache")
                        .version("1.0.0")
                        .description("API para consulta de preços de veículos baseados na Tabela FIPE, com cache otimizado em Redis."));
    }
}
