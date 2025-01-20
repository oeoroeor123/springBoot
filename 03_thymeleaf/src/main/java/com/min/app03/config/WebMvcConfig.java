package com.min.app03.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Spring Mvc Project의 servlet-context.xml 파일에서 하던 일을 여기서 처리할 수 있다.
// 정적 자원 처리, 인터셉터
@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  // 정적 자원 처리
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    WebMvcConfigurer.super.addResourceHandlers(registry);
    
    registry.addResourceHandler("/static/**")  // 주소
            .addResourceLocations("classpath:/static/"); // 디렉터리
    
  }
}
