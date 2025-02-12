package com.min.app15.service;


import java.util.List;

import org.springframework.data.domain.Pageable;

import com.min.app15.model.dto.MenuDto;

public interface MenuService {

  // 등록 추상 메소드
  MenuDto registMenu(MenuDto menuDto);
  
  // 수정
  MenuDto modifyMenu(MenuDto menuDto);
  
  // 삭제
  void deleteMenu(Integer menuCode);
  
  // 상세 조회
  MenuDto findMenuById(Integer menuCode);
  
  // 메뉴 목록 조회
  List<MenuDto> findMenuList(Pageable pageable);
  
  List<MenuDto> findByMenuPrice(Integer menuPrice);
  
  List<MenuDto> findByMenuName(String menuName);

}
