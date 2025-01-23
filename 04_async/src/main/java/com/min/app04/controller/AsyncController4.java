package com.min.app04.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.min.app04.dto.BoardDto;
import com.min.app04.mapper.IBoardMapper;
import com.min.app04.util.PageUtil;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AsyncController4 {
  
  @Autowired
  private IBoardMapper boardMapper;
  
  @Autowired
  private PageUtil pageUtil;
  
  @GetMapping(value="/index4")
  public void index4() {
    
  }

  @GetMapping(value="/list")
  public ResponseEntity<Map<String, Object>> list(HttpServletRequest request) {
    
    // 스크롤 할 때 마다 가져갈 게시글의 개수
    int display = 20;
    
    // 현재 페이지 번호
    int page = Integer.parseInt(request.getParameter("page"));
    
    // 전체 게시글의 개수
    int count = boardMapper.selectBoardCount();
    
    // 페이징 처리에 필요한 변수 계산
    pageUtil.setPaging(page, display, count);
    
    // 매퍼로 전달할 Map
    Map<String, Object> map = Map.of("offset", pageUtil.getOffset()
                                   , "display", display
                                   , "sort", "DESC");
    
    // 목록 받아오기
    List<BoardDto> list = boardMapper.selectBoardList(map);
    
    // 반환 값 (목록 + 전체 페이지 수)
    return ResponseEntity.ok()
                         .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                         .body(Map.of("pageCount", pageUtil.getPageCount()
                                     , "boardList",list));
    
    
    
    
  }
  
  
  
}
