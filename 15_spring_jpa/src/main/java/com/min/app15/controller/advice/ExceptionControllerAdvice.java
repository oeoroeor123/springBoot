package com.min.app15.controller.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.min.app15.model.exception.MenuNotFoundException;
import com.min.app15.model.message.ResponseErrorMessage;

@ControllerAdvice
public class ExceptionControllerAdvice {

  // 예외 처리 메소드 호출 (여러 반복되는 코드이기에, try-catch를 넣지 않고 한번에 @ExceptionHandler로 처리)
  @ExceptionHandler(MenuNotFoundException.class)
  // 자체적으로 만들어져있는 엔티티인 ResponseEntity 안에 ResponseErrorMessage를 넣어 내용을 담는다.
  public ResponseEntity<ResponseErrorMessage> handler(MenuNotFoundException e) {

    // 잘못된 요청이라는 응답 코드를 만든다.(.badRequest()) > 그 뒤에 .body()로 내용을 넣는다.
    // 없는 메뉴 코드 조회 시 발생되는 예외 처리
    return ResponseEntity.badRequest()
                  .body(ResponseErrorMessage.builder()
                  .code(10)
                  .message(e.getMessage())
                  .descride("존재하지 않는 메뉴 코드")
                  .build());
            
  }
 
}
