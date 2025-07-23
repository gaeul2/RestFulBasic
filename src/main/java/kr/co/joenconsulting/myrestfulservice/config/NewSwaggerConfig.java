package kr.co.joenconsulting.myrestfulservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title="My Restful Service API 명세서",
                    description = "Spring Boot로 개발하는 RESTFUL API 명세서 입니다.",
                    version = "v1.0.0")
)
@Configuration
@RequiredArgsConstructor
public class NewSwaggerConfig {
    @Bean
    public GroupedOpenApi customTestOpenAPI(){
        String[] paths = {"/users/**", "/admin/**"};

        return GroupedOpenApi.builder()
                .group("일반사용자와 관리자를 위한 User 도메인에 대한 API 입니다")
                .pathsToMatch(paths)
                .build();
    }

}
