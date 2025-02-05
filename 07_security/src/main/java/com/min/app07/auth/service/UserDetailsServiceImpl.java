package com.min.app07.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.min.app07.auth.model.CustomUserDetailes;
import com.min.app07.user.dto.LoginDto;
import com.min.app07.user.service.IUserService;

/**
 * 사용자의 아이디를 전달 받아 해당 사용자의 정보를 UserDetails 타입으로 반환하는
 * loadUserByUsername() 메소드만 가지고 있는 UserDetailsService 인터페이스를 구현한 클래스
 * 
 * Authentication Provider
 *  - 인증 로직에서 UserDetailsService를 통해 사용자의 세부 정보(UserDetails)를 획득한다.
 *  - 획득한 사용자 세부 정보와 사용자가 입력한 비밀번호의 일치 여부를 판단한다. (PasswordEncoder 이용)
 *  - 비밀번호가 일치하면 AuthenticationFilter에 인증 정보(Authentication 객체)를 전달한다. 이 정보는 SecurityContext에 저장된다.
 *  - 비밀번호가 일치하지 않으면 일반적으로 401 Unauthorized를 반환한다.
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private IUserService userService;
  
  /**
   * Authentication Provider가 호출하는 메소드
   * @param username 사용자가 입력한 유저 아이디
   * @return 데이터베이스에서 찾은 사용자의 정보를 담은 UserDetails 객체
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    
    // 데이터베이스에서 사용자 정보를 조회한다.
    LoginDto loginDto = userService.findByUsername(username);
    
    if(loginDto == null) {
      throw new UsernameNotFoundException("해당 회원 정보가 존재하지 않습니다.");
    }
    
    // 사용자가 존재하면 LoginDto 정보를 UserDetails 타입으로 반환한다.
    CustomUserDetailes customUserDetailes = new CustomUserDetailes();
    customUserDetailes.setLoginDto(loginDto);
    return customUserDetailes;
  }

}
