package com.min.app04.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.min.app04.dto.BookDto;

@Controller
public class AsyncController2 {

  @GetMapping("/index2")
  public void index2() {
    
  }
  
  /*
   * ResponseEntity 클래스
   * 1. 비동기 응답을 위해 반환 타입을 한번 감싸는 클래스
   * 2. @ResponseBody 가 필요하지 않다. (자동으로 적용 됨)
   */
  
  @GetMapping(value="/book/list")
  public ResponseEntity<List<BookDto>> list() {
   
    List<BookDto> books = new ArrayList<>();
    books.add(new BookDto("소나기", "황순원"));
    books.add(new BookDto("홍길동전", "허균"));
    
    // 응답 헤더 만들기
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE ); // @GetMapping의 produces를 대신하는 부분
       
    // 생성자를 이용한 ResponseEntity 객체 생성하기
    // 형식 : new ResponseEntity<>(응답 데이터, 응답 헤더, 응답 코드)
    return new ResponseEntity<>(books, headers, HttpStatus.OK);
  }

  
  @GetMapping(value="/book/detail")
  public ResponseEntity<BookDto> detail(@RequestParam String word) {
    
    // 응답 데이터
    BookDto bookDto = new BookDto(word, "김작가");

    // 정적 메소드(클래스이름.메소드 호출 방식)를 이용한 ResponseEntity 객체
    // 메소드 체이닝 방식으로 여러 메소드를 호출할 수 있음
    // 형식 : ResponseEntity.메소드 체이닝(응답 코드, 응답 헤더, 응답 데이터)
    return ResponseEntity.ok()
                         .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                         .body(bookDto);    
  }
 
  
  @PostMapping(value="/book/regist")
  public ResponseEntity<Map<String, Object>> regist(@RequestBody BookDto bookDto) {
    try {
      if(bookDto.getTitle().isEmpty() || bookDto.getAuthor().isEmpty())
        throw new RuntimeException("서버 오류 발생");
      return ResponseEntity.ok(Map.of("status", 200, "registed", bookDto)); // 성공 시, 성공한 데이터 반환
    } catch (Exception e) {
      e.printStackTrace(); 
      return ResponseEntity.internalServerError() // 500번 오류 코드 내용
                          .body(Map.of("status", 500, "message", e.getMessage())); // 실패 시, 실패 메시지 반환
    }
  }

  
}
