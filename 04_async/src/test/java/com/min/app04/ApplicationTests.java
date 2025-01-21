package com.min.app04;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.min.app04.mapper.IBoardMapper;

@SpringBootTest
class ApplicationTests {

   @Autowired
   private IBoardMapper boardMapper;
  
  
	@Test
	void contextLoads() {
	  Assertions.assertThat(boardMapper.selectBoardCount()).isEqualTo(1000);
	}

}
