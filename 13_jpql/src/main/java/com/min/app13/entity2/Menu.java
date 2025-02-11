package com.min.app13.entity2;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "menu2")
@Table(name = "tbl_menu")
public class Menu {

  @Id
  @Column(name = "menu_code")
  private Integer menuCode;
  
  @Embedded // @Embeddable 이 명시된 클래스를 포함한다.
  private MenuInfo menuInfo;
  
  @Column(name = "category_code")
  private Integer categoryCode;
  
  @Column(name = "orderable_status")
  private String orderableStatus;
  
  public Menu() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public String toString() {
    return "Menu [menuCode=" + menuCode + ", menuInfo=" + menuInfo + ", categoryCode=" + categoryCode
        + ", orderableStatus=" + orderableStatus + "]";
  }

  public Menu(Integer menuCode, MenuInfo menuInfo, Integer categoryCode, String orderableStatus) {
    super();
    this.menuCode = menuCode;
    this.menuInfo = menuInfo;
    this.categoryCode = categoryCode;
    this.orderableStatus = orderableStatus;
  }

  public Integer getMenuCode() {
    return menuCode;
  }

  public void setMenuCode(Integer menuCode) {
    this.menuCode = menuCode;
  }

  public MenuInfo getMenuInfo() {
    return menuInfo;
  }

  public void setMenuInfo(MenuInfo menuInfo) {
    this.menuInfo = menuInfo;
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
