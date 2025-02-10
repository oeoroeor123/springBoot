package com.min.app11.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
  
  /*
   * @JoinColumn
   * @OneToMany(일대다) 관계에서 사용하는 Annotation으로,
   * menu 엔티티와 category 엔티티를 category_code로 묶어 join하는 것을 의미한다. 
   */
  @JoinColumn(name = "category_code")
  /*
   * @OneToMany
   * 하나의 Category에 여러 Menu가 연결되어 있으므로 
   * Category 구성 시 여러 Menu를 저장할 List<Menu>를 추가하고 @OneToMany Annotation을 추가한다.
   */
  @OneToMany(
      cascade = CascadeType.PERSIST, // Category 엔티티를 등록할 때 연관 관계에 있는 Menu 엔티티를 함께 등록한다.
      // fetch = FetchType.EAGER // 열심히 조회한다는 의미. 연관 관계를 조회할 때 연관 테이블을 모두 조회한다.(카테고리 조회 시 메뉴도 함께 조회한다.)
      fetch = FetchType.LAZY     // 게으르게 조회한다는 의미(지연 로딩). 연관 관계를 조회할 때 현재 테이블만 조회하고, 나중에 연관 관계의 테이블이 필요하면 그 때 조회한다.
      )
  private List<Menu> menuList;

  public Category() {
    // TODO Auto-generated constructor stub
  }
  
  @Override
  public String toString() {
    return "Category [categoryCode=" + categoryCode + ", categoryName=" + categoryName + ", refCategoryCode="
        + refCategoryCode + ", menuList=" + menuList + "]";
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
