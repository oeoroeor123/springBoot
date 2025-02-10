package com.min.app12.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/* 
 * 단방향 객체로 만드는 경우의 Menu와 Category 엔티티
 * 
 * Menu                                          Category
 *  M                                                1   
 * @JoinColumn(name = "category_code")  @JoinColumn(name = "category_code")
 * @ManyToOne                           @OneToMany
 * private Category category;           private List<Menu> menuList;
 * 
 * --------------------------------------------------------------------------------- 
 * 
 * 양방향 객체로 만드는 경우 두 연관 관계 중에서 주인(Owner)를 정한다.
 * 주인(Owner)은 비즈니스 로직 상에서 더 중요한 엔티티를 찾는게 아닌 단순히 외래키 관리자에게 부여한다.(외래키를 가지고 있는 엔티티가 주인이다.)
 * 이 예제의 주인은 Menu 엔티티이다. (category_code를 외래키로 가지고 있는 엔티티)
 * 주인이 아닌 Category 엔티티에 주인 객체의 필드명을 mappedBy 속성으로 등록해 줘야 한다.
 * 
 * Menu                                          Category
 *  M                                                1   
 * @JoinColumn(name = "category_code")  
 * @ManyToOne                           @OneToMany(mappedBy = "category")
 * private Category category;           private List<Menu> menuList;
 */
@Entity(name = "menu")
@Table(name = "tbl_menu")
public class Menu {
  
  @Id
  @Column(name = "menu_code")
  private Integer MenuCode;

  @Column(name = "menu_name")
  private String menuName;
  
  @Column(name = "menu_price")
  private Integer menuPrice;
  
  @JoinColumn(name = "category_code")
  @ManyToOne
  private Category category;
  
  @Column(name = "orderable_status")
  private String orderableStatus;
  
  
  public Menu() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public String toString() {
    return "Menu [MenuCode=" + MenuCode + ", menuName=" + menuName + ", menuPrice=" + menuPrice + ", category="
        + category.toString() + ", orderableStatus=" + orderableStatus + "]";
  }

  public Integer getMenuCode() {
    return MenuCode;
  }

  public void setMenuCode(Integer menuCode) {
    MenuCode = menuCode;
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
  
  
  
}
