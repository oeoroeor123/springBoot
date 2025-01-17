package com.min.app02.service;

import java.util.List;

import com.min.app02.dto.BoardDto;

import jakarta.servlet.http.HttpServletRequest;

public interface IBoardService {

  List<BoardDto> getBoardList();
  BoardDto getBoardById(int boardId);
  String registBoard(BoardDto boardDto);
  String modifyBoard(HttpServletRequest request);
  String removeBoard(int boardId);
}
