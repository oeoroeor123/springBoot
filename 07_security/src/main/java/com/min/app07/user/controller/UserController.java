package com.min.app07.user.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.min.app07.user.dto.SignupDto;
import com.min.app07.user.service.IUserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
  
  private final IUserService userService;
  
  // a 태그로 받는 매핑은 get 매핑을 사용
  // 주소를 경로로 인식
  @GetMapping("/user/signup")  
  public void signup() {}
  
  // 매핑 방식이 다르면 주소가 동일해도 작동 가능
  // 파라미터만 다르면 메소드명이 같아도 구현 가능
  @PostMapping("/user/signup")
  public String signup(SignupDto signupDto, Model model) {
    // map으로 받는 타입이 모두 String ("message", "path")
    Map<String, String> map = userService.signup(signupDto);
    model.addAttribute("message", map.get("message"));
    return map.get("path");
  }
}
