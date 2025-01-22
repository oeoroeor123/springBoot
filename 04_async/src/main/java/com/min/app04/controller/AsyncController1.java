package com.min.app04.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.min.app04.dto.BoardDto;
import com.min.app04.service.IBoardService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AsyncController1 {

  private final IBoardService boardService;
  
  // Spring Boot 프로젝트의 Spring Web 디펜던시에는
  // jackson-databind 라이브러리가 포함되어 있다. (Spring MVC 프로젝트에서는 직접 mvnrepository를 통해 pom.xml에 추가해야 함)
  // 이 라이브러리가 Java 데이터와 json 데이터의 상호 변환을 담당한다.  
  
  @GetMapping(value={"/board/list", "/board/list.json"}
         // , produces="application/json") // 이 메소드의 응답 데이터는 JSON 타입이다.(application/json: 제이슨 데이터의 웹 타입)
            , produces=MediaType.APPLICATION_JSON_VALUE) // 위 주석 내용과 동일 (다른 표기법)
  @ResponseBody // 반환 값을 요청한 곳으로 보낸다. (페이지 이동x, 포워드x, 리다이렉트x)
  public Map<String, Object> list(HttpServletRequest request) {
    return boardService.getBoardList(request);
  }
  
  // 응답 데이터를 XML 데이터로 제공할 수 있다.
  // jackson-dataformat-xml 디펜던시가 이 역할을 수행할 수 있다.
  // 별도로 pom.xml에 추가해야 한다.
  
  @GetMapping(value="/board/list.xml"
         // , produces="application/xml")               // 이 메소드의 응답 데이터는 xml 타입이다.
            , produces=MediaType.APPLICATION_XML_VALUE) // 위 주석 내용과 동일 (다른 표기법)
  @ResponseBody // 반환 값을 요청한 곳으로 보낸다. 
  public Map<String, Object> listXml(HttpServletRequest request) {
    return boardService.getBoardList(request);
  }
  
  @PostMapping(value="/board/regist"
             , produces=MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, Object> regist(BoardDto boardDto) {
    
    return boardService.registBoard(boardDto);
  }
  
  @PostMapping(value="/board/singleUpload"
             , produces=MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, Object> singleUpload(MultipartHttpServletRequest multipartRequest) {
    System.out.println(multipartRequest.getParameter("nickname"));
    MultipartFile profile = multipartRequest.getFile("profile");
    System.out.println(profile.getOriginalFilename());
    return null;
  }
  
  @PostMapping(value="/board/multipleUpload"
             , produces=MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, Object> multipleUpload(MultipartHttpServletRequest multipartRequest) {
    System.out.println(multipartRequest.getParameter("notice"));
    List<MultipartFile> attaches = multipartRequest.getFiles("attaches");
    attaches.forEach(attach -> System.out.println(attach.getOriginalFilename()));
    return null;
  }
  
  @PostMapping(value="/board/addUser"
             , produces=MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, Object> addUser(@RequestBody Map<String, Object> map) { // json 문자열 데이터가 요청 본문에 포함되어 있기 때문에 @requestBody를 이용해서 해당 json 문자열을 받는다.
                                         // 받을 때는 DTO 또는 Map을 이용한다.
                                         // HttpServletRequest, @RequestParam 으로 받을 수 없다.
    System.out.println(map);
    return null;
  }
    
  
  
}
