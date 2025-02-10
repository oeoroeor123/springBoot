package com.min.app12.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Entity(name = "category")
@Table(name = "tbl_category")
public class Category {
  
  @Id
  @Column(name = "category_code")
  private Integer categoryCode;
  
  @Column(name = "category_name")
  private String categoryName;
  
  @Column(name = "ref_category_code")
  private Integer refCategoryCode;
  
  @OneToMany(mappedBy = "category")
  private List<Menu> menuList;
  
  public Category() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public String toString() {
    return "Category [categoryCode=" + categoryCode + ", categoryName=" + categoryName + ", refCategoryCode="
        + refCategoryCode + "]";
  }

  public Integer getCategoryCode() {
    return categoryCode;
  }

  public void setCategoryCode(Integer categoryCode) {
    this.categoryCode = categoryCode;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  public Integer getRefCategoryCode() {
    return refCategoryCode;
  }

  public void setRefCategoryCode(Integer refCategoryCode) {
    this.refCategoryCode = refCategoryCode;
  }

  public List<Menu> getMenuList() {
    return menuList;
  }

  public void setMenuList(List<Menu> menuList) {
    this.menuList = menuList;
  }
  
}
