package com.min.app13.entity4;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "category4")
@Table(name = "tbl_category")
public class Category {

  @Id
  @Column(name = "category_code")
  private Integer categoryCode;
  
  @Column(name = "category_name")
  private String categoryName;
  
  @Column(name = "ref_category_code")
  private Integer refCategoryCode;
  
  public Category() {
    // TODO Auto-generated constructor stub
  }

  public Category(Integer categoryCode, String categoryName, Integer refCategoryCode) {
    super();
    this.categoryCode = categoryCode;
    this.categoryName = categoryName;
    this.refCategoryCode = refCategoryCode;
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
  
  
}
