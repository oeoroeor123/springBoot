package com.min.app02;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.min.app02.dto.BoardDto;
import com.min.app02.mapper.IBoardMapper;

@SpringBootTest
class MapperTests {
	
	@Autowired
	IBoardMapper boardMapper;
	

	@Test
	void now_테스트() {
		Assertions.assertThat(boardMapper.now().split(" ")[0]).isEqualTo("2025-01-17");
	}
	
	@Test
	void list_테스트() {
	  Assertions.assertThat(boardMapper.selectBoardList().size()).isEqualTo(1);
	}
	
	@Test
	void detail_테스트() {
	  Assertions.assertThat(boardMapper.selectBoardById(1).getContents()).isEqualTo("contents");
	  Assertions.assertThat(boardMapper.selectBoardById(1).getTitle()).isEqualTo("title");
	}
	
	@Test
	void insert_테스트() {
	  BoardDto boardDto = BoardDto.builder()
	                         .title("추가 제목")
	                         .contents("추가 내용")
	                         .build();
	  Assertions.assertThat(boardMapper.insertBoard(boardDto)).isEqualTo(1);
	  System.out.println(boardDto.getBoardId()); // 삽입된 key값 출력
	}
	
	@Test
	void update_테스트() {
	 Assertions.assertThat(boardMapper.updateBoard("수정 제목", "수정 내용", 1)).isEqualTo(1); 
	}
	
	@Test
	void delete_테스트() {
	  Assertions.assertThat(boardMapper.deleteBoard(1)).isEqualTo(1);
	}

}
