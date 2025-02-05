package com.min.app07.config;

import java.io.IOException;
import java.net.URLEncoder;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 로그인이 실패하면 해당 요청에 대한 응답을 작성한 커스텀 핸들러
 * AuthenticationFailureHandler 인터페이스를 구현한 SimpleUrlAuthenticationFailureHandler 클래스를 상속해야 한다.
 */

@Configuration
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

  /**
   * 사용자의 잘못된 로그인 시도를 처리하는 핸들러 메소드
   * @param request 사용자 요청 객체
   * @param response 서버 응답 객체
   * @param exception 예외 처리 객체
   */
  
  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException, ServletException {
    
    String message = null;
    
    
    if(exception instanceof BadCredentialsException) {
      message = "아이디가 없거나 비밀번호가 일치하지 않습니다.";
    } else if (exception instanceof InternalAuthenticationServiceException) {
      message = "서버의 사용자 인증 서비스에 오류가 발생했습니다.";
    } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
      message ="인증 요청이 거부되었습니다.";
    } else {
      message ="알 수 없는 오류가 발생했습니다.";
    }
    
    // 로그인 실패 시 이동할 경로
    setDefaultFailureUrl("/auth/login?loginfailmessage=" + URLEncoder.encode(message, "UTF-8"));
    
    // 아래의 부모 메소드 호출은 다음 로직을 진행하라는 의미이다.
    super.onAuthenticationFailure(request, response, exception);
  }
}
