package com.min.app15.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.min.app15.model.dto.MenuDto;
import com.min.app15.model.entity.Menu;
import com.min.app15.repository.MenuRepository;

import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

  private final MenuRepository menuRepository;
  private final ModelMapper modelMapper;
  

  @Override
  public MenuDto registMenu(MenuDto menuDto) {
    // menuDto를 menu 엔티티로 바꾼다.
    Menu menu = modelMapper.map(menuDto, Menu.class);
    
    // menu 엔티티 전달
    // .save() : CrudRepository가 제공하는 메소드, persist()와 commit() 기능을 내포하고 있음 
    menuRepository.save(menu);
    
    // menuDto 반환
    return menuDto;
  }
  
  @Override
  public MenuDto modifyMenu(MenuDto menuDto) {
    
    // foundById() 메소드의 호출 결과 엔티티는 영속 컨텍스트에 저장된다.
    Menu foundMenu = menuRepository.findById(menuDto.getMenuCode()).orElse(null);
    
    // 영속 컨텍스트에 저장된 엔티티를 변경하면 해당 변경 내용이 데이터베이스에 반영된다. (== dirty checking)
    foundMenu.setMenuName(menuDto.getMenuName());
    foundMenu.setMenuPrice(menuDto.getMenuPrice());
    foundMenu.setCategoryCode(menuDto.getCategoryCode());
    foundMenu.setOrderableStatus(menuDto.getOrderableStatus());
     
    return menuDto;
  }
  
  @Override
  public void deleteMenu(Integer menuCode) {
    
    // menuRepository.deleteById(menuCode); // 없는 메뉴는 어떻게 할 것인가? 처리할 수 없기 때문에 find 기반 delete 로 바꾼다.
    Menu foundMenu = menuRepository.findById(menuCode).orElse(null);
    
    menuRepository.delete(foundMenu);
  }
  
  @Override
  public MenuDto findMenuById(Integer menuCode) {
   
    /*
     * 쿼리 메소드 (Query Method)
     * 1. JPQL을 메소드로 대신 처리할 수 있도록 제공하는 기능
     * 2. 메소드의 이름을 이용해서 JPQL을 생성하고 조회한다.
     * 3. 메소드의 이름은 "find + 엔티티 이름 (생략 가능) + By + 변수 이름" 규칙을 사용한다.
     * 4. 쿼리 메소드 유형
            KEYWORD          | METHOD                      |JPQL
     *      -----------------|-----------------------------|---------------------------------------
     *   1) And              | findByCodeAndName           | WHERE m.code = ?1 AND m.name = ?2
     *   2) Or               | findByCodeOrName            | WHERE m.code = ?1 OR m.name = ?2
     *   3) Not              | findByNameNot               | WHERE m.name <> ?1
     *   4) Between          | findByPriceBetween          | WHERE m.price BETWEEN 1? AND ?2
     *   5) LessThan         | findByPriceLessThan         | WHERE m.price < ?1
     *   6) LessThanEqual    | findByPriceLessThanEqual    | WHERE m.price < ?1
     *   7) GreaterThan      | findByPriceGreaterThan      | WHERE m.price > ?1
     *   8) GreaterThanEqual | findByPriceGreaterThanEqual | WHERE m.price >= ?1
     *   9) IsNull           | findByNameIsNull            | WHERE m.name IS NULL
     *  10) (Is)NotNull      | findByNameIsNotNull         | WHERE m.name IS NOT NULL
     *  11) Like             | findByNameLike              | WHERE m.name LIKE ?1
     *  12) StartingWith     | findByNameStartingWith      | WHERE m.name LIKE ?1 || '%'
     *  13) EndingWith       | findByNameEndingWith        | WHERE m.name LIKE '%' || ?1
     *  14) Containing       | findByNameContaining        | WHERE m.name LIKE '%' || ?1 || '%'
     *  15) OrderBy          | findByNameOrderByCodeDesc   | WHERE m.name = ?1 ORDER BY m.code DESC
     *   
     */
    
    Menu foundMenu = menuRepository.findById(menuCode).orElse(null);
    
    
    return modelMapper.map(foundMenu, MenuDto.class);
  }
  
  @Override
  public List<MenuDto> findMenuList(Pageable pageable) {
    
    Page<Menu> menuList = menuRepository.findAll(pageable);
    
    return menuList.map(menu -> modelMapper.map(menu, MenuDto.class))
                   .toList(); // Immutable List를 반환한다.
  }
  
  @Override
  public List<MenuDto> findByMenuPrice(Integer menuPrice) {
   
    // menuRepository에 아래와 같은 메소드들이 입력되어 있는지 체크 필요
    // List<Menu> menuList = menuRepository.findByMenuPriceGreaterThanEqual(menuPrice);
    // List<Menu> menuList = menuRepository.findByMenuPriceGreaterThanEqual(menuPrice, Sort.by("menuPrice").descending());
    List<Menu> menuList = menuRepository.findByMenuPriceGreaterThanEqualOrderByMenuPriceDesc(menuPrice);
    
    
    return menuList.stream()
                   .map(menu -> modelMapper.map(menu, MenuDto.class))
                   .toList();
  }

  @Override
  public List<MenuDto> findByMenuName(String menuName) {
    
    List<Menu> menuList = menuRepository.findByMenuNameContaining(menuName);
    
    return menuList.stream()
                   .map(menu -> modelMapper.map(menu, MenuDto.class))
                   .toList();
  }
  
  
  
  
}
