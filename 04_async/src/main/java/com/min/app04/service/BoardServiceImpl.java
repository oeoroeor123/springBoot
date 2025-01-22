package com.min.app04.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.min.app04.dto.BoardDto;
import com.min.app04.mapper.IBoardMapper;
import com.min.app04.util.PageUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements IBoardService {

  private final IBoardMapper boardMapper;
  private final PageUtil pageUtil;
  
  @Override
  public Map<String, Object> getBoardList(HttpServletRequest request) {
    
    // 페이징 처리를 위한 파라미터 page, display
    
    // page 파라미터 한번 감싸기
    Optional<String> optPage = Optional.ofNullable(request.getParameter("page"));
    // 실제 파라미터가 없으면 1을 꺼내기
    int page = Integer.parseInt(optPage.orElse("1"));
    
    // display 파라미터 한번 감싸기
    Optional<String> optDisplay = Optional.ofNullable(request.getParameter("display"));
    // 실제 파라미터가 없으면 20을 꺼내기
    int display = Integer.parseInt(optDisplay.orElse("20"));
    
    // 페이징 처리를 위한 게시글 갯수 count
    int count = boardMapper.selectBoardCount();
    
    // 페이징 처리에 필요한 모든 변수 처리하기
    pageUtil.setPaging(page, display, count);
    
    // offset
    int offset = pageUtil.getOffset();
    
    // 정렬을 위한 파마리터 sort
    Optional<String> optSort = Optional.ofNullable(request.getParameter("sort"));
    String sort = optSort.orElse("DESC");
    
    // 게시글 목록 가져오기 (전달 : offset, display, sort를 저장한 Map을 만들어 하나씩 가져온다.)
    List<BoardDto> boardList = boardMapper.selectBoardList(Map.of("offset", offset,
                                                                  "display", display,
                                                                  "sort", sort));
    
    // 페이징 가져오기 (전달 : 현재 서비스가 동작할 주소, 정렬 방식, 목록 개수, 검색 같은 추가 파라미터들)
    String paging = pageUtil.getAsyncPaging(Map.of("display", display, "sort", sort));
   
    // 결과 반환하기
    return Map.of("boardList", Map.of("board",boardList),
                  "count", count,
                  "paging", paging,
                  "offset", offset);
  }

  @Override
  public BoardDto getBoardById(int boardId) {
    return boardMapper.selectBoardById(boardId);
  }

  @Override
  public Map<String, Object> registBoard(BoardDto boardDto) {
    int status = 0;
    String msg = null;
    try {
      boardMapper.insertBoard(boardDto);
      status = 200;
      msg = "등록 성공";
    } catch (Exception e) {
      e.printStackTrace();
      status = 500;
      msg = "등록 실패";
    }
    return Map.of("status", status
                , "msg", msg
                , "registed", boardDto);
  }

@Override
public Map<String, String> modifyBoard(BoardDto boardDto) {
  String mapping = null;
  String msg = null;
  try {
    boardMapper.updateBoard(boardDto);
    mapping = "/list.do";
    msg = "수정 성공";
  } catch (Exception e) {
    e.printStackTrace();
    mapping = "/modify.do";
    msg = "수정 실패";
  }
  return Map.of("mapping", mapping, "msg", msg);
}

  @Override
  public String removeBoard(int boardId) {
    return boardMapper.deleteBoard(boardId) == 1? "삭제 성공" : "삭제 실패";
  }

}
