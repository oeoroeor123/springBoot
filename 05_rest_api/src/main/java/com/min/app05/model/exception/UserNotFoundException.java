package com.min.app05.model.exception;

/**
 * 조회된 사용자가 없는 경우 발생하는 예외입니다.
 * 조회된 사용자가 없다고 스프링 부트가 예외를 발생시키지는 않기 때문에
 * 직접 예외를 던져야 합니다.
 */
public class UserNotFoundException extends Exception {

  private static final long serialVersionUID = -8093926869254629654L;

  public UserNotFoundException(String message) {
    super(message);
  }

}
