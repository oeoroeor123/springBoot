package com.min.app11.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * Menu - Category
 * M    - 1
 * 
 * 일대다 연관 매핑 구성 시 자식 엔티티(M을 의미하는 Menu)에서 설정할 내용은 없다.
 */

@Entity(name = "menu")
@Table(name = "tbl_menu")
public class Menu {
  
  @Id
  @Column(name = "menu_code")
  private Integer menuCode;
  
  @Column(name = "menu_name")
  private String menuName;
  
  @Column(name = "menu_price")
  private Integer menuPrice;
  
  @Column(name = "category_code")
  private Integer categoryCode;
  
  @Column(name = "orderable_status")
  private String orderableStatus;

 
  // 컨트롤 + 스페이스바로 생성자 주입
  public Menu() {
    // TODO Auto-generated constructor stub
  }
  
  // toString 주입 
  @Override
  public String toString() {
    return "Menu [menuCode=" + menuCode + ", menuName=" + menuName + ", menuPrice=" + menuPrice + ", categoryCode="
        + categoryCode + ", orderableStatus=" + orderableStatus + "]";
  }

  // getter / setter 주입
  public Integer getMenuCode() {
    return menuCode;
  }

  public void setMenuCode(Integer menuCode) {
    this.menuCode = menuCode;
  }

  public String getMenuName() {
    return menuName;
  }

  public void setMenuName(String menuName) {
    this.menuName = menuName;
  }

  public Integer getMenuPrice() {
    return menuPrice;
  }

  public void setMenuPrice(Integer menuPrice) {
    this.menuPrice = menuPrice;
  }

  public Integer getCategoryCode() {
    return categoryCode;
  }

  public void setCategoryCode(Integer categoryCode) {
    this.categoryCode = categoryCode;
  }

  public String getOrderableStatus() {
    return orderableStatus;
  }

  public void setOrderableStatus(String orderableStatus) {
    this.orderableStatus = orderableStatus;
  }
}
