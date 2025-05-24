package com.mealDeals.capstone.config;

import org.springdoc.core.models.ApiInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.GroupedOpenApi;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/api/**")
                .build();
    }

    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfo("Meal Deals API",
                "API Documentation for Meal Deals System",
                "1.0.0",
                "",
                "support@mealdeals.com",
                "MIT License",
                "");
    }
}
