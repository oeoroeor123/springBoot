package com.min.app04.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.min.app04.dto.BoardDto;

@Mapper
public interface IBoardMapper {

  // 페이징 처리를 위한 갯수 구하기
  int selectBoardCount();
  
  // 페이징 처리를 위한 목록 구하기
  List<BoardDto> selectBoardList(Map<String, Object> param);
  
  // 상세보기
  BoardDto selectBoardById(int boardId);
  
  // 삽입
  int insertBoard(BoardDto boardDto);
  
  // 수정
  int updateBoard(BoardDto boardDto);
  
  // 삭제
  int deleteBoard(int boardId);
}
