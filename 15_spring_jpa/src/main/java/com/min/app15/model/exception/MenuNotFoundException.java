package com.min.app15.model.exception;

/*
 *                  <extends Exception>                                           <extends RuntimeException>
 *  구분            Exception (체크드 예외)                                       RuntimeException (언체크 예외)
 *  상속            Checked Exception의 부모 클래스                               Unchecked Exception의 부모 클래스
 *  필수 처리       예외 처리(try-catch) 와 회피(throws) 필수                     예외 처리(try-catch) 와 회피(throws) 필수 아님 (생략 가능)
 *  예외 처리 방식  try-catch로 반드시 처리하거나, 메서드에 throws 선언           try-catch로 처리하지 않아도 컴파일 오류 없음
 *  용도            예상 가능한 예외(예: 파일 입출력, 네트워크 통신, db 처리 등)  프로그램 로직에 의해 발생되는 오류(예: 잘못된 인자, 널 포인터 참조 등)  
 *
 *
 */

public class MenuNotFoundException extends Exception {

  /**
  * 예외 처리용 시리얼 처리를 위한 id 발굴
  */
  private static final long serialVersionUID = 544575198637096472L;

  public MenuNotFoundException(String message) {
    // 부모에게 자신이 전달 받은 메시지를 그대로 넘겨준다. (super = 부모)
    super(message);
  }
  
  
}
