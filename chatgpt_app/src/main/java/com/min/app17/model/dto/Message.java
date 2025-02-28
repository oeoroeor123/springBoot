package com.min.app17.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * RequestDto(요청Dto)와 ResponseDto(응답Dto)에서 사용하는 Message
 * 하나의 메시지를 나타내는 데이터 전송 객체
 * 
 * 1. role(역할)
 *    1) user      : 사용자(질문자)
 *    2) assistant : 사용자의 질문을 받아서 system에 전달하는 AI 비서 (user와 system 사이에 존재)
 *    3) system    : chatgpt
 * 
 * 2. content(내용)
 *    1) 대화 내용을 의미
 *    2) 한글과 영문은 사용하는 토큰에 차이가 있다. (한글 토큰이 영문보다 2배 이상 비싸다.)
 */

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Message {
  private String role;
  private String content;
}
