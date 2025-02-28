package com.min.app17.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * 응답Dto (openAI API 응답을 저장하는 객체)
 * 
 * 1. Choice
 *    응답 내용
 *    
 * 2. Usage
 *    토큰 사용량
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseDto {
  private List<Choice> choices;
  
  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Setter
  @ToString
  // Dto 클래스 안에 하나의 내부 Dto를 만든다. 내부 Dto에는 message 내용이 들어간다.
  public static class Choice {
    private Message message;
  }

}
