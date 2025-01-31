package com.min.app06.service;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

public interface IMemberService {
  // 회원 정보
  Map<String, Object> getMembers(HttpServletRequest request);
  // 회원 상세 정보
  Map<String, Object> getMemberById(int memId);
  // 회원 등록
  Map<String, Object> registMember(Map<String, Object> params);
  // 회원 수정
  Map<String, Object> modifyMember(Map<String, Object> params);
  // 회원 삭제
  int removeMember(int memId);
  // 선택 삭제
  int removeSelectMember(String memIds);
}
