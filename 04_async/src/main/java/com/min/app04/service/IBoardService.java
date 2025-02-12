package com.min.app04.service;

import java.util.Map;

import com.min.app04.dto.BoardDto;

import jakarta.servlet.http.HttpServletRequest;

public interface IBoardService {

  // 목록 서비스
  Map<String, Object> getBoardList(HttpServletRequest request);
 
  // 상세 서비스
  BoardDto getBoardById(int boardId);
  
  // 등록 서비스
  Map<String, Object> registBoard(BoardDto boardDto);
  
  // 수정 서비스
  Map<String, String> modifyBoard(BoardDto boardDto);
  
  // 삭제 서비스
  String removeBoard(int boardId);
}
