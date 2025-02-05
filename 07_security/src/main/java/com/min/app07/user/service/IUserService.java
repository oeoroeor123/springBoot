package com.min.app07.user.service;

import java.util.Map;

import com.min.app07.user.dto.LoginDto;
import com.min.app07.user.dto.SignupDto;

public interface IUserService {
  
  Map<String, String> signup(SignupDto signupDto);
  
  LoginDto findByUsername(String username); // 실제로는 userId를 의미함 (security 용어로 username으로 표기)

}
