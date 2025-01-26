package com.akmz.jep.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger springdoc-ui 구성 파일
 */
@Configuration
public class OpenApiConfig {

    private static final String BEARER_TOKEN_PREFIX = "Bearer";
    private static final String JWT = "JWT";

    @Bean
    public OpenAPI openAPI() {

        SecurityRequirement securityRequirement = new SecurityRequirement().addList(JWT);
        Components components = new Components()
                .addSecuritySchemes(JWT, new SecurityScheme()
                        .name(JWT)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme(BEARER_TOKEN_PREFIX)
                        .bearerFormat(JWT));

        return new OpenAPI()
                .components(new Components())
                .info(apiInfo())
                .components(components);

    }

    private Info apiInfo() {
        return new Info()
                .title("JEP API Document")
                .version("v1.0.0")
                .description("스프링 부트 JEP 프로젝트의 API 명세서");
    }
}
