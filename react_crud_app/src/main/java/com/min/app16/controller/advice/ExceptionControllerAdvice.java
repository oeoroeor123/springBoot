package com.min.app16.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.min.app16.model.message.ResponseErrorMessage;

@ControllerAdvice
public class ExceptionControllerAdvice {

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ResponseErrorMessage> handler(IllegalArgumentException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseErrorMessage.builder()
        .code(20)
        .message(e.getMessage())
        .decribe("존재하지 않는 블로그")
        .build());
    
  }
  
}
