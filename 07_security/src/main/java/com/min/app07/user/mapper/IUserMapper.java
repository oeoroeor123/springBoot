package com.min.app07.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.min.app07.user.dto.LoginDto;
import com.min.app07.user.dto.SignupDto;

@Mapper
public interface IUserMapper {
  
  int insertUser(SignupDto signupDto);
  
  LoginDto selectUserByUsername(String username); // 실제로는 userId를 의미함 (security 용어로 username으로 표기)
  
  
  
}
