package com.min.app09.entity3;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity(name="user3")
@Table(
       name = "tbl_user"   // 생성될 테이블의 이름 지정
     , schema = "db_boot9" // 생성될 테이블의 스키마(데이터베이스) 이름 지정
       )
@Access(AccessType.FIELD) // 클래스 레벨의 @Access 이므로 클래스의 모든 필드에 반영
                          // AccessType.FIELD는 필드에 직접 접근하는 방식을 의미, 디폴트 설정 (getter를 통하지 않고 바로 필드 값 확인 가능)

public class User {
  
  @Id
  @Column(name="user_id")
  private int userId;
  
  @Column(name="user_email")
  @Access(AccessType.PROPERTY) // userEmail 필드는 getter를 이용해서 필드 값을 확인할 수 있다.
  private String userEmail;
  
  @Column(name="user_password")
  private String userPassword;
  
  @Column(name="user_phone")
  private String userPhone;
  
  @Column(name="nickname")
  @Access(AccessType.PROPERTY) // nickname 필드는 getter를 이용해서 필드 값을 확인할 수 있다.
  private String nickname;
  
  @Column(name="address")
  private String address;

  @Column(name="create_dt")
  private Date createDt;
  
  @Column(name="user_role")
  // @Enumerated(EnumType.ORDINAL) // Enum 타입의 값을 순서대로 정수로 매핑 (0, 1, ...) @Enumerated 생략 시 디폴트값으로 사용됨
  @Enumerated(EnumType.STRING)     // Enum 타입의 값을 문자열로 매핑
  private UserRole userRole;

  
  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getUserEmail() {
    System.out.println("getUserEmail() 메소드 호출");
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
    return nickname + "님";
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

  public UserRole getUserRole() {
    return userRole;
  }

  public void setUserRole(UserRole userRole) {
    this.userRole = userRole;
  }

  @Override
  public String toString() {
    return "User [userId=" + userId + ", userEmail=" + userEmail + ", userPassword=" + userPassword + ", userPhone="
        + userPhone + ", nickname=" + nickname + ", address=" + address + ", createDt=" + createDt + ", userRole="
        + userRole + "]";
  }
  
}
