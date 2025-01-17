package com.min.app02.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.min.app02.dto.BoardDto;
import com.min.app02.mapper.IBoardMapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements IBoardService {
  
  private final IBoardMapper boardMapper;

  @Override
  public List<BoardDto> getBoardList() {
    return boardMapper.selectBoardList();
  }

  @Override
  public BoardDto getBoardById(int boardId) {
    return boardMapper.selectBoardById(boardId);
  }

  @Override
  public String registBoard(BoardDto boardDto) {
    return boardMapper.insertBoard(boardDto) == 1 ? "등록 성공" : "등록 실패";
  }

  @Override
  public String modifyBoard(HttpServletRequest request) {
    String title = request.getParameter("title");
    String contents = request.getParameter("contents");
    int boardId = Integer.parseInt(request.getParameter("boardId"));
    return boardMapper.updateBoard(title, contents, boardId) == 1 ? "수정 성공" : "수정 실패";
  }

  @Override
  public String removeBoard(int boardId) {
    return boardMapper.deleteBoard(boardId) == 1 ? "삭제 성공" : "삭제 실패";
  }

}
