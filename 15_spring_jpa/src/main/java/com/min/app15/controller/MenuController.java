package com.min.app15.controller;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.min.app15.model.dto.MenuDto;
import com.min.app15.model.exception.MenuNotFoundException;
import com.min.app15.model.message.ResponseMessage;
import com.min.app15.service.MenuService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MenuController {

  private final MenuService menuService;
  
  @PostMapping(value ="/menu", produces = "application/json")
  public ResponseMessage regist(@RequestBody MenuDto menuDto) {
    
    return ResponseMessage.builder()
                  .status(400)
                  .message("Menu 등록 성공!")
                  .results(Map.of("menu", menuService.registMenu(menuDto)))
                  .build();
    
    // POSTMAN 사이트를 통해 테스트를 진행한다.
    // http://localhost:8080/menu 
    // POST 방식
    /*
     * 메뉴 등록 테스트 코드
    {
    "menuName": "갯장어샤베트",
    "menuPrice": 20000,
    "categoryCode": 4 ,
    "orderableStatus": "Y" 
    }
     */
  }
  
  @PutMapping(value="/menu/{menuCode}", produces = "application/json")
  public ResponseMessage modify(
      @PathVariable(name = "menuCode") Integer menuCode
      // 여기서 발생한 예외는 exception handler가 잡아챈 후 예외 메시지를 출력한다. : throws MenuNotFoundException
    , @RequestBody MenuDto menuDto) throws MenuNotFoundException {
    
    menuDto.setMenuCode(menuCode);
    
    return ResponseMessage.builder()
                .status(200)
                .message("메뉴 수정 성공")
                .results(Map.of("menu", menuService.modifyMenu(menuDto)))
                .build();
  }
  
  // POSTMAN 사이트를 통해 테스트를 진행한다.
  // http://localhost:8080/menu 
  // PUT 방식

  /*
   * 메뉴 수정 테스트 코드
  {
    "menuName": "수정메뉴",
    "menuPrice": 10000,
    "categoryCode": 7 ,
    "orderableStatus": "Y" 
    }
  */
  
  @DeleteMapping(value="/menu/{menuCode}", produces = "application/json")
  //여기서 발생한 예외는 exception handler가 잡아챈 후 예외 메시지를 출력한다. : throws MenuNotFoundException
  public ResponseMessage remove(@PathVariable(name = "menuCode") Integer menuCode) throws MenuNotFoundException {
    
    menuService.deleteMenu(menuCode);
    
    return ResponseMessage.builder()
            .status(200)
            .message("메뉴 삭제 성공!")
            .results(null)
            .build();
  }
  
  @GetMapping(value = "/menu/{menuCode}", produces = "application/json")
  public ResponseMessage findMenuById(@PathVariable(name = "menuCode") Integer menuCode) throws MenuNotFoundException {
    
    return ResponseMessage.builder()
            .status(200)
            .message("메뉴 조회 성공!")
            .results(Map.of("menu", menuService.findMenuById(menuCode)))
            .build();
  }
  
  @GetMapping(value = "/menu", produces = "application/json")
   public ResponseMessage findMenuList(/* @PageableDefault */ Pageable pageable) {

 
    /*
     * Pageable 인터페이스
     * 1. 페이징 처리에 필요한 정보(size(=display), page, sort)를 처리하는 인터페이스
     * 2. Pageable 인터페이스 정보를 초기화 할 수 있다.
     *   방법 1) @PageableDefault Annotation 추가
     *   방법 2) 프로퍼티에 등록 (application.property)
     * 3. Pageable 인터페이스에 파라미터 전달하는 방법
     *   1) page : page=1
     *   2) size : size=10
     *   3) sort : sort=menuCode,desc 또는 sort=menuCode,asc (누구를, 어떤 방식으로)
     * 4. 주의사항
     *   파라미터 page=1 로 전달되면 Pageable 인터페이스는 2페이지로 인식한다. (시작 페이지가 0이기 때문에)
     *   Pageable 인터페이스의 page 값은 -1 처리해야 한다.
     */
    
    // Pageable 인터페이스의 page 값을 1 감소하는 코드 (page 파라미터 시작 페이지를 1로 만들기 위해, 기존은 0부터 시작임)
    // Pageable 값을 바꾼 Pageable을 반환한다.
    pageable = pageable.withPage(pageable.getPageNumber() -1);
   
    // System.out.println(pageable);
    
    return ResponseMessage.builder()
            .status(200)
            .message("메뉴 조회 성공!")
            .results(Map.of("menuList", menuService.findMenuList(pageable)))
            .build();
  }
  
  
  @GetMapping(value="/menu/price/{menuPrice}", produces = "application/json")
  public ResponseMessage findByMenuPrice(@PathVariable(name = "menuPrice") Integer menuPrice) {
    
    // 테스트 url : http://localhost:8080/menu/price/2000
    return ResponseMessage.builder()
          .status(200)
          .message(menuPrice + "원 이상 메뉴 조회 성공")
          .results(Map.of("menuList", menuService.findByMenuPrice(menuPrice)))
          .build();
  }
  
  @GetMapping(value="/menu/name/{menuName}", produces = "application/json")
  public ResponseMessage findByMenuName(@PathVariable(name = "menuName") String menuName) {
    
    // 테스트 url : http://localhost:8080/menu/name/마늘
    return ResponseMessage.builder()
           .status(200)
           .message(menuName + "이 포함된 메뉴 조회 성공")
           .results(Map.of("menulist", menuService.findByMenuName(menuName)))
           .build();
  }
  
  @GetMapping(value="/categories", produces = "application/json")
  public ResponseMessage findCategoryList() {
    
    // 테스트 url : http://localhost:8080/categories
    return ResponseMessage.builder()
                          .status(200)
                          .message("카테고리 목록 조회 성공")
                          .results(Map.of("categories", menuService.findByCategoryList()))
                          .build();
  }
  
  
}
