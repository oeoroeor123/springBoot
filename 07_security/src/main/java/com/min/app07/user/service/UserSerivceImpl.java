package com.min.app07.user.service;

import java.util.Map;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.min.app07.user.dto.LoginDto;
import com.min.app07.user.dto.SignupDto;
import com.min.app07.user.mapper.IUserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSerivceImpl implements IUserService {
  
  private final IUserMapper userMapper;
  private final PasswordEncoder passwordEncoder;
  
  @Override
  public Map<String, String> signup(SignupDto signupDto) {
    // 사용자가 입력한 비밀번호 암호화 처리하기 : passwordEncoder.encode(signupDto.getUserPassword())
    signupDto.setUserPassword(passwordEncoder.encode(signupDto.getUserPassword()));
    
    // Map에 저장되어 있는 반환할 데이터 (메시지, 가입 후 이동 경로)
    String message = null;
    String path = null;
    
    try {
      userMapper.insertUser(signupDto);
      message = "회원 가입 완료";
      path = "auth/login";
    } catch (DuplicateKeyException e) {
      // unique 처리한 user_id키가 중복될 경우 예외 발생
      e.printStackTrace();
      message = "중복된 아이디";
      path = "user/signup"; // 다시 가입 페이지로 이동
    } catch (Exception e) {
      e.printStackTrace();
      message = "서버 오류 발생";
      path = "user/signup";
    }
    
    return Map.of("message", message, "path", path);
  }
  
  @Override
  public LoginDto findByUsername(String username) {
    return userMapper.selectUserByUsername(username);
  }

}
