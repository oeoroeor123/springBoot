package com.min.app07.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.min.app07.common.UserRole;

@Configuration
public class SecurityConfig {

  @Autowired
  private LoginFailureHandler loginFailureHandler;
  
  /*
   * SecurityFilterChain
   * Spring Security의 기본 애플리케이션 보안 구성을 담당
   * 사용자가 SecurityFilterChain 빈을 등록하면 Spring Security의 보안 구성을 비활성화하고
   * 사용자가 직접 보안 구성을 정의할 수 있다.
   */
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    
    // URL에 따라 서버에 접근 가능한 권한을 부여한다.
    // security 기능 구현을 위해 람다식 표현을 사용한다.
    http.authorizeHttpRequests(auth -> {
      auth.requestMatchers("/", "/user/signup", "/auth/login").permitAll(); // requestMatchers에 등록된 모든 경로는 누구나 접속 가능하다.
      auth.requestMatchers("/admin/**").hasAnyAuthority(UserRole.ADMIN.getUserRole()); // "/admin"으로 시작하는 주소는 "ADMIN" UserRole(권한)을 가진 사람만 접속 가능하다.
      auth.anyRequest().authenticated(); // 모든 요청에 대해서 인증을 요구한다. 이 설정이 포함되면 로그인 설정을 반드시 별도로 설정해서 처리해야 한다.
    });
    
    // 로그인 설정 처리
    http.formLogin(login -> {
      login.loginPage("/auth/login"); // /auth/login 요청 시 로그인 페이지로 이동
      login.usernameParameter("userId"); // 로그인 페이지의 유저 아이디가 가진 파라미터, security 에서는 username(userId) 이름을 사용
      login.passwordParameter("userPassword"); // 로그인 페이지의 유저 비밀번호가 가진 파라미터, security 에서는 password(userPassword) 이름을 사용
      login.defaultSuccessUrl("/", true); // 로그인 성공 시 언제나(true) 메인 페이지("/")로 이동
                                          // 로그인 성공하면 세션에 인증 토큰이 저장된다.
      login.failureHandler(loginFailureHandler); // 로그인 실패 시 LoginFailureHandler가 동작
    });
    
    // 세션 관리
    http.sessionManagement(session -> {
      session.maximumSessions(1);     // 하나의 세션을 사용한다.
      session.invalidSessionUrl("/"); // 세션 만료 시, "/"로 이동한다. (브라우저 종료 또는 세션 만료 시간이 지난 경우)
    });
    
    // 로그아웃 관리
    http.logout(logout -> {
      logout.logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout")); // "/auth/logout/" 요청이 오면 로그아웃 한다.
      logout.invalidateHttpSession(true); // 로그아웃 시, 세션이 만료된다.
      logout.deleteCookies("JSESSIONID"); // 세션이 신규 생성되면 쿠키 값으로 세션 아이디가 저장된 JSESSIONID이 생기는데, 해당 쿠키값을 지운다.
      logout.logoutSuccessUrl("/"); // 로그아웃 시, "/"로 이동한다. 
    });
    
    return http.build(); // 마지막에 build로 마무리
  }
  
  
  /*
   * PasswordEncoder
   * BCrypt 방식의 암호화 알고리즘을 지원하는 BCryptPasswordEncoder 빈을 반환한다.
   * BCrypt 암호화 알고리즘
   *  - 가장 많이 사용하는 비밀번호 해싱 알고리즘 중 하나
   *  - 높은 보안, 호환성(데이터베이스에 저장하기 쉬운 데이터 생성), 신뢰성을 가짐
   */
  
  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
  
  /*
   * WebSecurityCustomizer
   * static 디렉터리 하위에 있는 모든 정적 리소스들에 대한 요청은 보안에서 제외하게 된다.
   * 람다식 표현법을 사용해 제외할 내용을 명시한다. (ignoring())
   */
  @Bean
  WebSecurityCustomizer webSecurityCustomizer() {
    // 제외할 경로는 requestMatchers안에 명시
    return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
  }
}
