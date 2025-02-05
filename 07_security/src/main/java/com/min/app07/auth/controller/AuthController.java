package com.min.app07.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

  // 경로와 주소를 일치시켰기 때문에 void 처리 후 로그인 페이지 구현
  @GetMapping("/auth/login")
  public void login() {}
  
  // 실제 구현 시, AdminController 별도 생성 후 코드 작성하기
  @GetMapping("/admin/page")
  public void admin() {}
  
}
