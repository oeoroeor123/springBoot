package com.min.app13.entity2;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable // 엔티티의 일부 데이터를 가지고 있는 클래스, 엔티티는 아니다. 엔티티에서 @Embedded 를 이용해 엔티티에 포함될 수 있다.
public class MenuInfo {
  
  @Column(name = "menu_name")
  private String menuName;
  
  @Column(name = "menu_price")
  private Integer menuPrice;
  
  public MenuInfo() {
    // TODO Auto-generated constructor stub
  }

  public MenuInfo(String menuName, Integer menuPrice) {
    super();
    this.menuName = menuName;
    this.menuPrice = menuPrice;
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

  @Override
  public String toString() {
    return "MenuInfo [menuName=" + menuName + ", menuPrice=" + menuPrice + "]";
  }
  
  

}
