package com.min.app05.model;

import java.util.Map;

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
public class ResponseMessage {
  private int status;                   // 응답 코드
  private String message;               // 응답 메시지
  private Map<String, Object> results;  // 응답 결과
}
