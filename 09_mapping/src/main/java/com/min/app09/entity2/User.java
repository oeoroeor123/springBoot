package com.min.app09.entity2;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;


// @Entity(name="user2")
@Table(
       name = "tbl_user" // 생성될 테이블의 이름 지정
     , schema = "db_boot9" // 생성될 테이블의 스키마(데이터베이스) 이름 지정
     , uniqueConstraints = { @UniqueConstraint(columnNames = { "user_phone", "create_dt" }) } // 두 개 이상의 칼럼을 묶어서 UNIQUE 제약 조건을 지정하는 경우에 필요
       )
@TableGenerator(
                name = "user_seq_table_generator"
              , table = "user_seq"                // 시퀀스 테이블의 이름
              
              // 아래 속성에서 사용한 값들은 모두 디폴트 값
              , pkColumnName = "sequence_name"    // 기본키 칼럼의 이름
              , pkColumnValue = "user_seq"        // 기본키 칼럼의 값
              
              , valueColumnName = "next_val"      // 일반 칼럼의 이름
              , initialValue = 0                  // 초기 값
              , allocationSize = 1                // 증가 값
    
    )
/*
 * @TableGenerator 이용해서 시퀀스 테이블을 만들 경우, 아래와 같이 만들어진다.
 * CREATE TABLE user_seq
 * (
 *   sequence_name VARCHAR(255) NOT NULL,
 *   next_val      BIGINT,
 *   PRIMARY KEY (sequene_name)  
 * ) Engine=InnoDB;
 */
public class User {
  
  @Id
  @Column(name="user_id")
  @GeneratedValue(
                 // strategy = GenerationType.IDENTITY  // 기본키 생성을 데이터베이스에 위임(mysql의 auto_increment)
                 // strategy = GenerationType.AUTO      // 기본키 생성을 데이터베이스에 따라서 자동으로 선택 (mysql의 경우 시퀀스 테이블, 오라클의 경우 시퀀스가 만들어진다.)
                 // strategy = GenerationType.SEQUENCE  // 시퀀스 데이터베이스 객체를 이용(오라클) 
                 
                  strategy = GenerationType.TABLE         // 키 값을 생성하는 별도의 테이블을 이용
                , generator = "user_seq_table_generator"  // @TableGenerator를 이용해서 키 값을 생성하는 별도의 테이블을 만든다.
      )  
  private int userId;
  
  @Column(name="user_email", unique = true) // 중복된 값을 가질 수 없다. 디폴트는 false
  private String userEmail;
  
  @Column(name="user_password", nullable = false) // NULL 값을 가질 수 없다. (NOT NULL) 디폴트는 true
  private String userPassword;
  
  @Column(name="user_phone", columnDefinition = "VARCHAR(13) DEFAULT '010-0000-0000'") // 직접 해당 칼럼의 DDL을 작성한다.
  private String userPhone;
  
  @Column(name="nickname", unique = true) // 중복된 값을 가질 수 없다. 디폴트는 false
  private String nickname;
  
  @Transient // 테이블을 생성할 때 무시되는 칼럼
  @Column(name="address")
  private String address;
  
  @Temporal(TemporalType.TIMESTAMP) // 칼럼 타입이 DATETIME
  // @Temporal(TemporalType.DATE) 칼럼 타입이 DATE
  // @Temporal(TemporalType.TIME) 칼럼 타입이 TIME
  @Column(name="create_dt")
  private Date createDt;
  
  @Column(name="user_role", length = 100) // length: 최대 문자열의 길이, 오직 String에서만 사용 가능하다. 디폴트는 VARCHAR(255)
  private String userRole;

  
  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  public String getUserPassword() {
    return userPassword;
  }

  public void setUserPassword(String userPassword) {
    this.userPassword = userPassword;
  }

  public String getUserPhone() {
    return userPhone;
  }

  public void setUserPhone(String userPhone) {
    this.userPhone = userPhone;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Date getCreateDt() {
    return createDt;
  }

  public void setCreateDt(Date createDt) {
    this.createDt = createDt;
  }

  public String getUserRole() {
    return userRole;
  }

  public void setUserRole(String userRole) {
    this.userRole = userRole;
  }

  @Override
  public String toString() {
    return "User [userId=" + userId + ", userEmail=" + userEmail + ", userPassword=" + userPassword + ", userPhone="
        + userPhone + ", nickname=" + nickname + ", address=" + address + ", createDt=" + createDt + ", userRole="
        + userRole + "]";
  }
  
}
