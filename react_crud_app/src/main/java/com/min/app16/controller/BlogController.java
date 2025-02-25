package com.min.app16.controller;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.min.app16.model.dto.BlogDto;
import com.min.app16.model.message.ResponseMessage;
import com.min.app16.service.BlogService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class BlogController {
  
  private final BlogService blogService;
  
  @GetMapping(value = "/blogs", produces = "application/json")
  public ResponseMessage list(Pageable pageable) {
    pageable = pageable.withPage(pageable.getPageNumber() - 1);
    return ResponseMessage.builder()
                .status(200)
                .message("블로그 목록 조회 성공")
                .results(Map.of("blogList", blogService.findBlogList(pageable)))
              .build();
  }
  
  @PostMapping(value = "/blogs", produces = "application/json")
  public ResponseMessage regist(@RequestBody BlogDto blogDto) {
    return ResponseMessage.builder()
        .status(200)
        .message("블로그 등록 성공")
        .results(Map.of("blog", blogService.registBlog(blogDto)))
      .build();
  }
  
  @GetMapping(value = "/blogs/{id}", produces = "application/json")
  public ResponseMessage detail(@PathVariable(name = "id") Integer id) {
    return ResponseMessage.builder()
                .status(200)
                .message("블로그 상세 조회 성공")
                .results(Map.of("blog", blogService.findBlogById(id)))
              .build();
  }
  
  @PutMapping(value = "/blogs/{id}", produces = "application/json")
  public ResponseMessage modify(@RequestBody BlogDto blogDto, @PathVariable(name = "id") Integer id) {
    blogDto.setId(id);
    return ResponseMessage.builder()
        .status(200)
        .message("블로그 수정 성공")
        .results(Map.of("blog", blogService.modifyBlog(blogDto)))
      .build();
  }
  
  @DeleteMapping(value = "/blogs/{id}", produces = "application/json")
  public ResponseMessage delete(@PathVariable(name = "id") Integer id) {
    blogService.deleteBlogById(id);
    return ResponseMessage.builder()
        .status(200)
        .message("블로그 삭제 성공")
        .results(null)
      .build();
  }
  
}
