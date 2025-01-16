package com.min.app01;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.min.app01.dto.BoardDto;
import com.min.app01.mapper.IBoardMapper;

@SpringBootTest
class ApplicationTests {
  
  @Autowired // 생성자 주입
  IBoardMapper boardMapper;

	@Test
	void 매퍼_객체_생성_테스트() {
	  Assertions.assertNotNull(boardMapper);
	}
	
	@Test
	void now_테스트() {
	  System.out.println("now: " + boardMapper.now());
	}
	
	@Test  
	void board_list_테스트() {
	  Assertions.assertEquals(1, boardMapper.selectBoardList().size());
	}
	
	@Test
	void board_detail_테스트() {
	  Assertions.assertEquals("title", boardMapper.selectBoardById(1).getTitle());
	}
	
	@Test
	void board_insert_테스트() {
	  BoardDto boardDto =boardDto.builder()
	      
	  Assertions.assertEquals(2, boardMapper.selectBoardList().size());
	}
	
	@Test
	void board_update_테스트() {
	  Assertions.assertEquals("title2", boardMapper.selectBoardById(2).getTitle());
	}
	
	@Test
	void board_delete_테스트() {
	  Assertions.assertEquals(1, boardMapper.selectBoardList().size());
	}

}
