package com.min.app05.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ResponseErrorMessage {

  private String code; // 에러 코드
  private String error; // 예외 메시지 (Exception의 message)
  private String description; // 예외 메시지 설명
}
