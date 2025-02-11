package com.min.app13.entity3;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "category3")
@Table(name = "tbl_category")
public class Category {
  
  // 양방향 관계에서 Category 엔티티는 주인이 아닌 엔티티이다.
  // 따라서 해당 Category 엔티티는 주인 엔티티 Menu 를 참조하고 있음을 명시해야 한다.
  
  @Id
  @Column(name = "category_code")
  private Integer categoryCode;
  
  @Column(name = "category_name")
  private String categoryName;
  
  @Column(name = "ref_category_code")
  private Integer refCategoryCode;
  
  // Category - Menu 는 일대다 관계이다. (하나의 카테고리에 여러 메뉴)
  // 주인이 아닌 엔티티임으로 mappedBy = "category" 적어줌 (주인 엔티티인 Menu의 참조 필드 이름)
  @OneToMany(mappedBy = "category")
  private List<Menu> menuList;
  
  public Category() {
    // TODO Auto-generated constructor stub
  }

  // 주인이 아닌 엔티티의 toString() 메소드 오버라이드 시
  // 주인 엔티티의 출력 구문은 제거해야 한다.
  // 그렇지 않으면 StackOverFlowError 가 발생한다. (메소드 무한 호출)
  @Override
  public String toString() {
    return "Category [categoryCode=" + categoryCode + ", categoryName=" + categoryName + ", refCategoryCode="
        + refCategoryCode + "]";
  }

  public Category(Integer categoryCode, String categoryName, Integer refCategoryCode, List<Menu> menuList) {
    super();
    this.categoryCode = categoryCode;
    this.categoryName = categoryName;
    this.refCategoryCode = refCategoryCode;
    this.menuList = menuList;
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
