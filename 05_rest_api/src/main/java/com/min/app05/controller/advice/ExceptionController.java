package com.min.app05.controller.advice;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.min.app05.model.ResponseErrorMessage;
import com.min.app05.model.ResponseErrorMessage;
import com.min.app05.model.exception.UserNotFoundException;

/*
 * @ControllerAdvice
 * 1. 애플리케이션 내의 모든 컨트롤러에서 발생하는 예외를 처리할 수 있습니다.
 * 2. Advice 이므로 AOP 기술이 적용된 Annotation 입니다.
 * 3. 컨트롤러에 포함되어야 할 예외 처리 코드를 따로 분리해 낼 수 있습니다.
 *   이로 인해 코드의 중복 문제를 해결하고, 서로의 관심사를 분리할 수 있습니다.
 */


@ControllerAdvice
public class ExceptionController {
  
  // 유효성 검사
  // 예외를 잡기 위해서는 포스트맨에서 먼저 에러를 내서 예외 이름을 확인 후 코드로 잡기 !

  @ExceptionHandler(DuplicateKeyException.class)
  public ResponseEntity<ResponseErrorMessage> handleUserRegistException1(DuplicateKeyException e) {

    
    ResponseErrorMessage errorMessage = ResponseErrorMessage.builder()
        .code("ERROR_CODE_00000")
        .error(e.getMessage())
        .description("기존 회원과 동일한 이메일이 입력되었습니다.")
        .build();
    
    return ResponseEntity.badRequest()
                         .body(errorMessage);
  }
  
  // 유효성 검사 (defaultMessage와 code로 오류 메시지를 구분함)
  
  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public ResponseEntity<ResponseErrorMessage> handleUserRegistException2(MethodArgumentNotValidException e) {
    
    String code = null;
    String error = null;
    String description = null;
    
    BindingResult bindingResult = e.getBindingResult();
    if(bindingResult.hasErrors()) { // 실제로 에러가 발생했는지 체크
      // 에러 메시지 (유효성 검사 설정 시, 작성한 메시지)
      error = bindingResult.getFieldError().getDefaultMessage(); // == @NotBlank(message = "이메일은 반드시 입력해야 합니다.")
      // 코드에 따른 code와 description 설정
      switch (bindingResult.getFieldError().getCode()){
      case "NotBlank":
        code = "ERROR_CODE_00001";
        description = "필수 입력 값이 누락되거나, 공백입니다.";
        break;
      case "Size":
         code = "ERROR_CODE_00002";
         description = "크기를 벗어난 값이 입력되었습니다.";
         break;
      }
    }
    
    ResponseErrorMessage errorMessage = ResponseErrorMessage.builder()
                         .code(code)
                         .error(error)
                         .description(description)
                         .build();
  
    return ResponseEntity.badRequest()
                         .body(errorMessage);
  
  }
  
  @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ResponseErrorMessage> handleUserRegistException3(MethodArgumentTypeMismatchException e) {
    
    ResponseErrorMessage errorMessage = ResponseErrorMessage.builder()
        .code("ERROR_CODE_00003")
        .error(e.getMessage())
        .description("잘못된 데이터가 입력되었습니다.")
      .build();
    
    return ResponseEntity.badRequest().body(errorMessage);
    
  }
  
  @ExceptionHandler(value = UserNotFoundException.class)
  public ResponseEntity<ResponseErrorMessage> handleUserRegistException4(UserNotFoundException e) {
    
    ResponseErrorMessage errorMessage = ResponseErrorMessage.builder()
        .code("ERROR_CODE_00004")
        .error(e.getMessage())
        .description("데이터를 찾을 수 없습니다.") // 포스트맨 url 마지막에 없는 userId 넣어서 테스트 : http://localhost:8080/users/343
        .build();
    
    // 정적 메소드로 처리하기 적절한 코드가 없어 생성자를 이용해 객체를 생성합니다. (Not Found 처리)
    return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    
  }
  
  
  
}
