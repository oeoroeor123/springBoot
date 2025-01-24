package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
  
  /*
   * Swagger UI (API 문서화, 테스트 기능 등을 제공)
   * http://localhost:8080/swagger-ui/index.html
   * 1. OpenAPI Specification의 이전 이름
   * 2. 초기 Swagger는 API를 설계하고 빌드하고 문서화하는데 사용되었음
   * 3. 현재 Swagger는 API 문서화, 테스트 기능 등을 제공하면서 API 개발자들의 생산성 향상에 도움을 줌
   * 4. SpringDoc OpenAPI Starter WebMVC UI 디펜던시가 필요 (pom.xml에 디펜던시 추가)
   *    (스프링부트 3.x 이후로 SpringFox는 지원하지 않음)  
   */
  
  @Bean
  OpenAPI openAPI() {
    
    
    final Info info = new Info().title("Spring Boot REST API")
                                .description("REST API 를 지원하는 회원 기능")
                                .version("1.0.0");
    
    return new OpenAPI()
                 .components(new Components())
                 .info(info);
  }
}
