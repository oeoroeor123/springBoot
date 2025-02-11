package com.min.app13.entity2;

public class CategoryInfo {

  private Integer categoryCode;
  
  private String categoryName;
  
  public CategoryInfo() {
    // TODO Auto-generated constructor stub
  }

  public CategoryInfo(Integer categoryCode, String categoryName) {
    super();
    this.categoryCode = categoryCode;
    this.categoryName = categoryName;
  }

  @Override
  public String toString() {
    return "CategoryInfo [categoryCode=" + categoryCode + ", categoryName=" + categoryName + "]";
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
  
  
}
