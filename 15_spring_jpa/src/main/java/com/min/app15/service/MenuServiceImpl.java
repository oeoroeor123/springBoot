package com.min.app15.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.min.app15.model.dto.CategoryDto;
import com.min.app15.model.dto.MenuDto;
import com.min.app15.model.entity.Category;
import com.min.app15.model.entity.Menu;
import com.min.app15.model.exception.MenuNotFoundException;
import com.min.app15.repository.CategoryRepository;
import com.min.app15.repository.MenuRepository;

import lombok.RequiredArgsConstructor;

@Transactional // find 메소드는 select 이기 때문에 각 메소드에 별도로 readonly 처리를 해줌
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

  private final MenuRepository menuRepository;
  private final CategoryRepository categoryRepository;
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
  // 컨트롤러로 예외를 넘김 : throws MenuNotFoundException
  public MenuDto modifyMenu(MenuDto menuDto) throws MenuNotFoundException {
    
    // findById() 메소드가 던지는 IllegalArgumentException 처리를 하려면 아래와 같이 한다.
    // null일 경우, null 반환이 아닌 예외를 던진다. 예외를 던질 때, 람다식 표현법을 이용해 새로운 예외를 만들어 처리한다. 
    // foundMenu = menuRepository.findById(menuDto.getMenuCode()).orElseThrow(() -> new IllegalArgumentException());
    
    // foundById() 메소드의 호출 결과 엔티티는 영속 컨텍스트에 저장된다.
    // 없으면 NULL 반환
    Menu foundMenu = menuRepository.findById(menuDto.getMenuCode()).orElse(null);
    
    // null일 경우, MenuNotFoundException 예외를 만들어 반환한다
    if(foundMenu == null)
      throw new MenuNotFoundException("해당 메뉴가 없습니다.");
    
    // 영속 컨텍스트에 저장된 엔티티를 변경하면 해당 변경 내용이 데이터베이스에 반영된다. (== dirty checking)
    foundMenu.setMenuName(menuDto.getMenuName());
    foundMenu.setMenuPrice(menuDto.getMenuPrice());
    foundMenu.setCategoryCode(menuDto.getCategoryCode());
    foundMenu.setOrderableStatus(menuDto.getOrderableStatus());
     
    return menuDto;
  }
  
  @Override
  public void deleteMenu(Integer menuCode) throws MenuNotFoundException {
    
    // menuRepository.deleteById(menuCode); // 없는 메뉴는 어떻게 할 것인가? 처리할 수 없기 때문에 find 기반 delete 로 바꾼다.
    Menu foundMenu = menuRepository.findById(menuCode).orElse(null);
    
    if(foundMenu == null)
      throw new MenuNotFoundException("해당 메뉴가 없습니다.");
    
    menuRepository.delete(foundMenu);
  }
  
  @Transactional(readOnly = true) // 트랜잭션 동작이 필요 없어 읽기 전용으로 세팅함
  @Override
  public MenuDto findMenuById(Integer menuCode) throws MenuNotFoundException {
   
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
    
    if(foundMenu == null) {
      throw new MenuNotFoundException("해당 코드로 존재하는 메뉴가 없습니다.");
    }
    
    
    return modelMapper.map(foundMenu, MenuDto.class);
  }
  
  @Transactional(readOnly = true) // 트랜잭션 동작이 필요 없어 읽기 전용으로 세팅함
  @Override
  public List<MenuDto> findMenuList(Pageable pageable)  {
    
    Page<Menu> menuList = menuRepository.findAll(pageable);
    
    
    return menuList.map(menu -> modelMapper.map(menu, MenuDto.class))
                   .toList(); // Immutable List를 반환한다.
  }
  
  @Transactional(readOnly = true) // 트랜잭션 동작이 필요 없어 읽기 전용으로 세팅함
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

  @Transactional(readOnly = true) // 트랜잭션 동작이 필요 없어 읽기 전용으로 세팅함
  @Override
  public List<MenuDto> findByMenuName(String menuName) {
    
    List<Menu> menuList = menuRepository.findByMenuNameContaining(menuName);
    
    return menuList.stream()
                   .map(menu -> modelMapper.map(menu, MenuDto.class))
                   .toList();
  }
  
  // @Transactional(readOnly = true) // 트랜잭션 동작이 필요 없어 읽기 전용으로 세팅함
  @Override
  public List<CategoryDto> findByCategoryList() {
    
    // 3보다 큰 카테고리 코드를 가진 리스트 뽑아내기
    Integer categoryCode = 3;
    
    List<Category> categories = categoryRepository.findByCategoryCodeGreaterThan(categoryCode);
    
    // 카테고리를 하나씩 꺼내서 modelMapper를 통해 categoryDto 형식으로 바꿔준 후 리스트로 뽑아낸다.
    return categories.stream()
                     .map(category -> modelMapper.map(category, CategoryDto.class))
                     .toList();
  }
  
  
  
  
}
