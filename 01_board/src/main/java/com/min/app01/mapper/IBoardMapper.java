package com.min.app01.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.min.app01.dto.BoardDto;

@Mapper // 마이바티스 매퍼
public interface IBoardMapper {

  @Select("SELECT NOW()")
  String now();
  
  @Select("SELECT board_id, title, contents, create_dt FROM tbl_board ORDER BY board_id DESC")
  List<BoardDto>selectBoardList();
  
  @Select("SELECT board_id, title, contents, create_dt FROM tbl_board WHERE board_id = #{boardId}")
  BoardDto selectBoardById(@Param("boardId") int board_id); 
  
  @Insert("INSERT INTO tbl_board VALUES(null, {title}, {contents},NOW())")
  BoardDto insertBoardDto(BoardDto boardDto);
  
  @Update("UPDATE tbl_board SET title = #{title}', WHERE board_id = #{boardId}")
  BoardDto updateBoardDto(@Param("boardId") int board_id);
  
  @Delete("DELETE FROM tbl_board WHERE board_id=2")
  BoardDto deleteBoardDto(@Param("boardId") int board_id);
  
}