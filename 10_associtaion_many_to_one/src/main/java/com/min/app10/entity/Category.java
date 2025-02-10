package com.min.app10.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Menu - Category
 * M    - 1
 * 
 * 다대일 연관 매핑 구성 시 부모 엔티티(1을 의미하는 Category)에서 설정할 내용은 없습니다.
 */
@Entity(name = "category")
@Table(name = "tbl_category")
public class Category {

  @Id
  @Column(name = "category_code")
  private int categoryCode;
  
  @Column(name = "category_name")
  private String categoryName;
  
  @Column(name = "ref_category_code")
  private Integer refCategoryCode;
  
  public Category() {
    // TODO Auto-generated constructor stub
  }

  public Category(int categoryCode, String categoryName, Integer refCategoryCode) {
    super();
    this.categoryCode = categoryCode;
    this.categoryName = categoryName;
    this.refCategoryCode = refCategoryCode;
  }

  public int getCategoryCode() {
    return categoryCode;
  }

  public void setCategoryCode(int categoryCode) {
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

  @Override
  public String toString() {
    return "Category [categoryCode=" + categoryCode + ", categoryName=" + categoryName + ", refCategoryCode="
        + refCategoryCode + "]";
  }
  
}