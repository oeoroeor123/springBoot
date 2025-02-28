package com.min.app17.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * 요청Dto(openAI API에 보낼 요청 객체)
 * 
 * 1. model
 *    1) 사용할 모델
 *    2) 예: gpt-4o, gpt-4o-mini 등
 *    
 * 2. messages
 *    1) 대화 리스트
 *    2) 각 Message는 role과 content로 구성된다.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestDto {
  
  private String model;
  private List<Message> messages;

}
