package com.min.app07.auth.model;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.min.app07.user.dto.LoginDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Spring Security에서는 로그인 사용한 정보를 UserDetails 타입의 데이터로 반환해야 한다.
 * 따라서, LoginDto가 가지고 있는 로그인 사용자 정보를 UserDetails에 전달해서 처리해야 한다.
 * UserDetailesService 인터페이스를 구현한 UserDetailsServiceImpl 클래스에서 loadUserByUsername() 메소드가 반환하는 타입
 */

@NoArgsConstructor
@Getter
@Setter
public class CustomUserDetailes implements UserDetails {
  
  // 직렬화를 위한 serialVersionUID 생성 (경고 메시지에서 선택하여 생성함)
  private static final long serialVersionUID = -2971719294336425389L;

  // LoginDto
  private LoginDto loginDto;
  
  /**
   * 사용자의 권한 목록(Collection == list)을 반환
   * UsernamePasswordAuthenticationToken에 사용자의 권한 목록을 전달할 때 사용
   * 람다식 표현을 사용해 userRole 저장
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    loginDto.getUserRoles().forEach(userRole -> authorities.add(() -> userRole));
    return authorities;
  }

  /**
   * 사용자의 비밀번호를 반환
   * UsernamePasswordAuthenticationToken과 사용자의 비밀번호를 비교할 때 사용
   */
  @Override
  public String getPassword() {
    return loginDto.getUserPassword();
  }

  /**
   * 사용자의 아이디를 반환
   * UsernamePasswordAuthenticationToken과 사용자의 아이디를 비교할 때 사용
   */
  @Override
  public String getUsername() {
    return loginDto.getUserId();
  }
  
  /**
   * 계정 만료 여부를 반환 (true : 만료 안됨)
   * false 이면 해당 계정을 사용할 수 없음
   */
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }
  
  /**
   * 계정의 잠김 여부를 반환 (true : 잠기지 않음)
   * false 이면 해당 계정을 사용할 수 없음
   * 패스워드의 반복 실패로 일시적인 계정의 잠금 처리 같은 작업 (또는 휴면 처리)
   */
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }
  
  /**
   * 계정의 비밀번호 만료 여부를 반환 (ture : 만료 안됨)
   * false이면 해당 계정을 사용할 수 없음
   */
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }
  
  /**
   * 계정의 활성화 여부를 반환 (ture : 활성화 됨)
   * false 이면 해당 계정을 사용할 수 없음
   * 보통 데이터 삭제는 즉시 이뤄지지 않고 일정 기간 보관 후 삭제됨
   */
  @Override
  public boolean isEnabled() {
    return true;
  }

  
}
