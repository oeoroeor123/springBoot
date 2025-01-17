package com.min.app02.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.min.app02.dto.BoardDto;

@Mapper
public interface IBoardMapper {

	String now();
	
  List<BoardDto>selectBoardList();
	
  BoardDto selectBoardById(int boardId);
  
  int insertBoard(BoardDto boardDto);
  
  int updateBoard(@Param("title") String title, @Param("contents") String contents, @Param("boardId") int boardId);

  int deleteBoard(@Param("boardId") int boardId);
}
