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

@Entity(name = "menu")
@Table(name = "tbl_menu")
public class Menu {
  
  @Id
  @Column(name = "menu_code")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer menuCode;
  
  @Column(name = "menu_name")
  private String menuName;
  
  @Column(name = "menu_price")
  private Integer menuPrice;
  
  @Column(name = "category_code")
  private Integer categoryCode;
  
  @Column(name = "orderable_status")
  private String orderableStatus;
  
}
