package com.min.app17.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

  // application.properties에 지정한 key 값 부여
  @Value("${openai.api.key}")
  private String OPENAI_API_KEY;
  
  @Bean
  RestTemplate restTemplate() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getInterceptors().add((request, body, execution) -> {
      request.getHeaders().add("Authorization","Bearer " + OPENAI_API_KEY);
      return execution.execute(request, body);
    });
    return restTemplate;
  }
  
}
