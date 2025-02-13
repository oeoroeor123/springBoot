package com.min.app15.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString

@Entity(name = "category")
@Table(name = "tbl_category")
public class Category {
  
  @Id
  @Column(name = "category_code")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer categoryCode;
  
  @Column(name = "category_name")
  private String categoryName;
  
  @Column(name = "ref_category_code")
  private Integer refCategoryCode;

}
