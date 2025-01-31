package com.min.app06.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
  
  @GetMapping("/index")
  public void index() {
    
  }
}
