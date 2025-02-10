package com.min.app10.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/*
 * Menu - Category
 * M    - 1
 */

@Entity(name = "menu")
@Table(name = "tbl_menu")
public class Menu {

  @Id
  @Column(name = "menu_code")
  private int menuCode;
  
  @Column(name = "menu_name")
  private String menuName;
  
  @Column(name = "menu_price")
  private int menuPrice;
  
  /*
   * @JoinColumn : ManyToOne(다대일) 관계에서 사용하는 Annotation 입니다. (외래키에 지정하는 칼럼입니다.)
   * 
   * 1. name             : Category 엔티티가 참조하는 테이블의 칼럼 이름입니다.
   * 2. table            : Category 엔티티가 참조하는 테이블의 이름입니다.
   * 3. nullable         : NULL 값을 허용하는지 여부를 지정합니다.
   * 4. unique           : 유일성 제약 조건을 추가하는지 여부를 지정합니다.
   * 5. columnDefinition : 이 참조 칼럼(외래키)에 대한 SQL DDL을 직접 작성할 수 있습니다.
   */
  
  //Category 엔티티가 참조하는 Menu 엔티티에 관련된 속성을 작성
  @JoinColumn(name = "category_code"   // name = "tbl_menu의 칼럼 이름"
            , table ="tbl_menu"        // table = "tbl_menu"
      )

  /*
   * Menu 엔티티 - Category 엔티티의 관계는 다대일 관계입니다.
   * 따라서, @ManyToOne Annotation 을 사용합니다.
   */
  
  /*
   * cascade = CascadeType.PERSIST
   * "연속성 전이"라고 합니다.
   * "영속성 전이"란, 특정 엔티티를 영속화 할 때(즉, 저장할 때) 연관된 엔티티도 함께 영속화한다는 의미입니다. 
   * commit()을 호출하는 경우, 영속 컨텍스트의 영속 객체를 insert 하는 쿼리문이 실행됩니다.
   * 메뉴 테이블의 값을 저장할 땐 반드시 존재하는 카테고리 코드 값이 필요합니다. (참조 무결성)
   * 삽입할 Menu 엔티티에 저장된 Category 엔티티를 먼저 카테고리 테이블에 저장해야 한다는 의미입니다.
   * 
   * ** Menu 엔티티를 저장할 때 연관된 category 엔티티도 함께 저장되도록 설정해야 합니다.
   * ** 다대일_객체_insert_test() 테스트 코드 수행 전에 설정해 놓고 수행합니다 !!
   */
  
  @ManyToOne(cascade = CascadeType.PERSIST)
  private Category category; // Category 엔티티를 참조한다. (dto에서 다른 dto를 활용하는 것처럼) 
  
  @Column(name = "orderable_status")
  private String orderableStatus;
  
  
  public Menu() {
    // TODO Auto-generated constructor stub
  }

  public Menu(int menuCode, String menuName, int menuPrice, Category category, String orderableStatus) {
    super();
    this.menuCode = menuCode;
    this.menuName = menuName;
    this.menuPrice = menuPrice;
    this.category = category;
    this.orderableStatus = orderableStatus;
  }

  public int getMenuCode() {
    return menuCode;
  }

  public void setMenuCode(int menuCode) {
    this.menuCode = menuCode;
  }

  public String getMenuName() {
    return menuName;
  }

  public void setMenuName(String menuName) {
    this.menuName = menuName;
  }

  public int getMenuPrice() {
    return menuPrice;
  }

  public void setMenuPrice(int menuPrice) {
    this.menuPrice = menuPrice;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public String getOrderableStatus() {
    return orderableStatus;
  }

  public void setOrderableStatus(String orderableStatus) {
    this.orderableStatus = orderableStatus;
  }

  @Override
  public String toString() {
    return "Menu [menuCode=" + menuCode + ", menuName=" + menuName + ", menuPrice=" + menuPrice + ", category="
        + category + ", orderableStatus=" + orderableStatus + "]";
  }
  
}