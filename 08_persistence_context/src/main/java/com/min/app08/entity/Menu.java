package com.min.app08.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

/*
 * @Entity
 * JPA에서 엔티티 클래스(관계형 데이터베이스의 테이블과 매핑되는 자바 클래스)임을 명시하는 Annotation
 * 일반 클래스와는 달리 다른 패키지에 같은 이름을 가진 엔티티가 존재할 수 없다.
 * 다른 패키지에 같은 이름의 엔티티가 존재하면 name 속성으로 이들을 구분해야 한다.
 */

// @Entity를 적용시킴으로써, 관계형 데이터베이스의 테이블과 연결(매핑)되는 자바 클래스가 된다.
@Entity
/*
 * @Table
 * JPA에서 매핑되는 테이블을 정할 때 사용할 수 있다.
 */
@Table(name = "tbl_menu")

/*
 * @DynamicUpdate
 * JPA에서 변경된 엔티티의 필드만 관계형 데이터베이스에 반영하는 Annotation
 * ex) menuName 필드만 변경 시, 전체 필드가 아닌 변경 원하는 필드만 변경됨
 */
@DynamicUpdate
public class Menu {

  /*
   * @Id
   * JPA에서 해당 필드가 엔티티의 식별자임을 명시하는 Annotation
   * 매핑된 테이블의 기본키(PK)와 연결할 수 있다.
   */
  @Id // PK 기본키임을 명시
  
  /*
   * @GeneratedValue
   * JPA에서 해당 필드는 엔티티의 식별자를 자동으로 생성함을 명시하는 Annotation
   * 매핑된 테이블의 기본키가 생성되는 방식에 따라서 strategy 속성을 지정할수 있다.
   */
  // 테스트를 위해 주석 처리 중!!원할때 주석 해제!!  @GeneratedValue(strategy = GenerationType.IDENTITY) // mysql PK 키의 AUTO_INCREMENT와 동일한 형태
  
  /*
   * @Column
   * JPA에서 해당 필드가 테이블의 칼럼임을 명시하는 Annotation
   * 칼럼 이름을 name 속성으로 지정하고, 실제 칼럼엔 camelcase로 명시한다.
   */
  @Column(name = "menu_code") // 테이블의 칼럼임을 명시, camelcase로 쓰기 위해 name에 원래 칼럼 이름을 작성해줌 
  private int menuCode;
  
  @Column(name = "menu_name")
  private String menuName;
  
  @Column(name = "menu_price")
  private int menuPrice;
  
  @Column(name = "orderable_status")
  private String orderableStatus;

  @Column(name = "category_code")
  private int categoryCode;
  

  public int getMenuCode() {
    return menuCode;
  }

  public void setMenuCode(int menuCode) {
    this.menuCode = menuCode;
  }

  public String getMenuName() {
    return menuName;
  }

  public void setMenuName(String menuName) {
    this.menuName = menuName;
  }

  public int getMenuPrice() {
    return menuPrice;
  }

  public void setMenuPrice(int menuPrice) {
    this.menuPrice = menuPrice;
  }

  public int getCategoryCode() {
    return categoryCode;
  }

  public void setCategoryCode(int categoryCode) {
    this.categoryCode = categoryCode;
  }

  public String getOrderableStatus() {
    return orderableStatus;
  }

  public void setOrderableStatus(String orderableStatus) {
    this.orderableStatus = orderableStatus;
  }

  @Override
  public String toString() {
    return "Menu [menuCode=" + menuCode + ", menuName=" + menuName + ", menuPrice=" + menuPrice + ", categoryCode="
        + categoryCode + ", orderableStatus=" + orderableStatus + "]";
  }
  
  
  
  
  
}
