package com.RODS.configuration;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Spring doc config.
 */
@Configuration
public class SpringDocConfig {

    /**
     * Custom OpenAPI bean.
     * @return the OpenAPI configuration
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Boot REST API")
                        .description("Spring Boot REST API for Restaurant App")
                        .version("1.0.0")
                        .license(new License().name("Apache License Version 2.0").url("https://www.apache.org/licenses/LICENSE-2.0"))
                        .contact(new Contact()
                                .name("Tetiana Rohalska")
                                .url("https://github.com/trohalska/restaurant")
                                .email("trogalska2208@gmail.com")));
    }
}
