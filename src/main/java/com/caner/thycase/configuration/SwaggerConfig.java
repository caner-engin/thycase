package com.caner.thycase.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String TITLE = "THYCase APIs";
    private static final String DESC = "This is Swagger UI environment generated for the " + TITLE + " specification";
    private static final String LICENSE_URL = "http://www.apache.org/licenses/LICENSE-2.0.html";
    private static final String LICENSE = "Apache 2.0";
    private static final String VERSION = "1.0";

    @Bean
    public OpenAPI springRewardsApi() {
        return new OpenAPI().info(new Info().title(TITLE)
                        .description(DESC)
                        .version(VERSION)
                        .license(new License().name(LICENSE).url(LICENSE_URL)))
                .externalDocs(new ExternalDocumentation().description(DESC).url(""));
    }
}