package com.min.app14.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
  
  public Menu() {
    // TODO Auto-generated constructor stub
  }

  public Menu(Integer menuCode, String menuName, Integer menuPrice, Integer categoryCode, String orderableStatus) {
    super();
    this.menuCode = menuCode;
    this.menuName = menuName;
    this.menuPrice = menuPrice;
    this.categoryCode = categoryCode;
    this.orderableStatus = orderableStatus;
  }

  @Override
  public String toString() {
    return "Menu [menuCode=" + menuCode + ", menuName=" + menuName + ", menuPrice=" + menuPrice + ", categoryCode="
        + categoryCode + ", orderableStatus=" + orderableStatus + "]";
  }

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
