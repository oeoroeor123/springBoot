package com.min.app05.controller;

import java.util.Map;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.min.app05.model.ResponseMessage;
import com.min.app05.model.dto.UserDto;
import com.min.app05.service.IUserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

// REST API 서비스 개발을 위한 컨트롤러 : @Controller + @ResponseBody
@RestController
@RequiredArgsConstructor
public class UserController {

  private final IUserService userService;
  
  // @Validated : 유효성 검사를 수행할 객체에 추가
  // 실제 유효성 검사는 해당객체에서 수행
  @PostMapping("/users")
  public ResponseMessage registUser(@Validated @RequestBody UserDto userDto) throws Exception {
    return ResponseMessage.builder()
              .status(201) // 201 Created (post 요청이 성공적으로 처리 되었으며, 자원이 생성되었음을 나타내는 성공 상태 응답 코드) 
              .message("사용자 등록 성공")
              .results(Map.of("user", userService.registUser(userDto)))
              .build();
  }
  
  @PutMapping("/users/{userId}") // 경로안에 변수가 있는 경우, '경로 변수'
  public ResponseMessage modifyUser(@PathVariable int userId, @RequestBody UserDto userDto) throws Exception {
    userDto.setUserId(userId); // @PathVariable로 받은 userId 가져오기 (따로 넘어옴)
    return ResponseMessage.builder()
                          .status(201) // 수정 또한 삽입과 동일한 응답 코드를 사용
                          .message("사용자 정보 수정 성공")
                          .results(Map.of("user", userService.modifyUser(userDto)))
                          .build();
  }
  
  @DeleteMapping("/users/{userId}")
  public ResponseMessage removeUser(@PathVariable int userId) {
    return ResponseMessage.builder()
                          .status(204) // 요청이 성공하였으나 해당 데이터를 참조할 수 없음을 의미함, 삭제 후 204를 사용할 수 있으나 주로 200을 사용함
                          .message("사용자 삭제 성공")
                          .results(null)
                          .build();
  }
  
  @GetMapping("/users")
  public ResponseMessage selectUser(HttpServletRequest request) throws Exception {
    return ResponseMessage.builder()
                          .status(200) // 요청이 성공하여 가져온 리소스를 메시지 본문으로 전송
                          .message("사용자 목록 조회 성공")
                          .results(Map.of("users", userService.getUsers(request)))
                          .build();
  }
  
  @GetMapping("/users/{userId}")
  public ResponseMessage selectUserById(@PathVariable int userId) throws Exception {
    return ResponseMessage.builder()
                          .status(200) // 요청이 성공하여 가져온 리소스를 메시지 본문으로 전송
                          .message("사용자 조회 성공")
                          .results(Map.of("user", userService.getUserById(userId)))
                          .build();
  }
  
}
