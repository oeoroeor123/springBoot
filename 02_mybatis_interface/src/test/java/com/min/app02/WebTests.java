package com.min.app02;

// import static : 정적 메서드를 직접 가져오는 방식, 클래스 이름없이 바로 사용 가능
// ex) Assertions.assertThat >> assertThat으로 바로 사용 가능
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.ModelAndView;

@SpringBootTest
@AutoConfigureMockMvc // MockMvc 자동 설정 코드, 수동 설정은 springmvc app12 참고
public class WebTests {

  @Autowired
  MockMvc mockMvc; // web test 할 때 사용하는 객체
  
  @Test
  void mockMvc_객체_생성_테스트() {
    // mockMvc 객체가 null이 아니면 통과
    assertThat(mockMvc).isNotNull();
  }
  
  @Test
  void 목록_보기_테스트() throws Exception {
    // 실제 http 요청을 시뮬레이션하는 메소드
    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/list.do")) // /list.do 요청 실시
                                 .andDo(MockMvcResultHandlers.print()) // 요청과 응답 결과를 출력
                                 .andExpect(MockMvcResultMatchers.status().isOk()) // 응답 코드가 200 (Ok)이면 통과
                                 .andReturn();
    
    // 요청 결과인 ModelAndView 객체 가져오기
    ModelAndView mav = mvcResult.getModelAndView();
    // 반환되는 뷰 가져오기
    System.out.println(mav.getViewName());
    // ModelAndView 객체 중 모델에 담긴 키 값 = 목록(boardList) 가져오기
    System.out.println(mav.getModelMap().get("boardList"));
    System.out.println(mav.getModelMap().get("paging"));
  }
  
  @Test
  void 상세_보기_테스트() throws Exception {

      MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/detail.do").param("boardId", "1")) // /detail.do 요청 실시 (get 방식)
                                   .andDo(MockMvcResultHandlers.print())              // 요청과 응답 결과를 화면에 출력
                                   .andExpect(MockMvcResultMatchers.status().isOk())  // 응답 코드가 200 (OK)이면 통과
                                   .andReturn();
      
      ModelAndView mav = mvcResult.getModelAndView();
      System.out.println(mav.getViewName());
      System.out.println(mav.getModelMap().get("board")); // 모델을 map으로 변경하여 board(키 값)을 가져옴
    }
  
  @Test
  void 신규_등록_테스트() throws Exception {
    
   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/regist.do") // /regitst.do 요청 실시 (post 방식)                      .param("title","신규 제목") // 신규 등록할 제목
                                                               .param("contents", "신규 내용")) // 신규 등록할 내용
       
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is3xxRedirection()) // 302 Found : Redirect 응답 상태 코드(이 상태를 수신하는 브라우저는 새 페이지로 Redirect 합니다.)
                                .andReturn();
   
   System.out.println(mvcResult.getModelAndView().getViewName()); // redirect 경로
   System.out.println(mvcResult.getFlashMap().get("msg")); // 실행 결과 메시지
  }
  
  @Test
  void 수정_테스트() throws Exception {
    
    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/modify.do") // post 방식
                                                                .param("title", "수정 제목") 
                                                                .param("contents", "수정 내용")
                                                                .param("boardId", "1")) // 수정할 boardId 작성
                                 .andDo(MockMvcResultHandlers.print())
                                 .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                                 .andReturn();
    
    System.out.println(mvcResult.getModelAndView().getViewName());
    System.out.println(mvcResult.getFlashMap().get("msg"));
  }
  
  @Test
  void 삭제_테스트() throws Exception {
    
    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/remove.do") // get 방식
                                                                .param("boardId", "1")) // 삭제할 boardId
                                 .andDo(MockMvcResultHandlers.print())
                                 .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                                 .andReturn();
    
    System.out.println(mvcResult.getModelAndView().getViewName());
    System.out.println(mvcResult.getFlashMap().get("msg"));
  }
  
}
