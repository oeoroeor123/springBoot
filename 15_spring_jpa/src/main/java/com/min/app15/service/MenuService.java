package com.min.app15.service;


import java.util.List;

import org.springframework.data.domain.Pageable;

import com.min.app15.model.dto.CategoryDto;
import com.min.app15.model.dto.MenuDto;
import com.min.app15.model.exception.MenuNotFoundException;

public interface MenuService {

  // 등록 추상 메소드
  MenuDto registMenu(MenuDto menuDto);
  
  // 수정
  MenuDto modifyMenu(MenuDto menuDto) throws MenuNotFoundException;
  
  // 삭제
  void deleteMenu(Integer menuCode)throws MenuNotFoundException;
  
  // 상세 조회
  MenuDto findMenuById(Integer menuCode) throws MenuNotFoundException;
  
  // 메뉴 목록 조회
  List<MenuDto> findMenuList(Pageable pageable);
  
  List<MenuDto> findByMenuPrice(Integer menuPrice);
  
  List<MenuDto> findByMenuName(String menuName);
  
  // 카테고리 조회
  List<CategoryDto> findByCategoryList();

}
