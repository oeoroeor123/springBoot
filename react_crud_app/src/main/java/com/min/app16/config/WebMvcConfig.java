package com.min.app16.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
  
  /*
   * CORS (Cross-Origin Resource Sharing)
   * 1. 교차 출처 리소스 공유
   * 2. 브라우저가 자신의 출처가 아닌 다른 출처로부터 자원을 로딩하는 것을 허용하도록
   *    서버가 허가해주는 HTTP 헤더 기반 알고리즘입니다.
   * 3. 보안 상의 이유로 브라우저는 스크립트에서 시작한 교차 출처 HTTP 요청을 제한합니다.
   * 4. XMLHttpRequest, fetch(), axios() 모두 동일 출처 정책을 따릅니다.
   * 5. 스프링 부트 서버(http://localhost:8080)와 리액트 서버(http://localhost:3000)는 서로
   *    포트번호가 다르기 때문에 다른 출처로 인식됩니다. 따라서 CORS 허용 작업이 필요합니다.
   */
  
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("Authorization", "Cache-Control", "Content-Type");
  }

}
