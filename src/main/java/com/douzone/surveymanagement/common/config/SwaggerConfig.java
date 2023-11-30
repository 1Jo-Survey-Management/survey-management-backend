package com.douzone.surveymanagement.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 스웨거 설정을 담당하는 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/

@Configuration
public class SwaggerConfig {

    private static final String BASE_PACKAGE = "com.douzone.surveymanagement";
    private static final String API_NAME = "Survey Plus API";
    private static final String API_DESCRIPTION = "Survey Plus API 명세";
    private static final String API_VERSION = "0.0.1";
    private static final String PATH_MATCH = "/api/**";

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
            .group(API_NAME)
            .packagesToScan(BASE_PACKAGE)
            .pathsToMatch(PATH_MATCH)
            .build();
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(
                new Info().title(API_NAME)
                    .description(API_DESCRIPTION)
                    .version(API_VERSION)
            );
    }
}
