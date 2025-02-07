package com.min.app09.entity1;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

// @Entity(name="user1") // 다른 패키지에 같은 이름의 User 엔티티가 존재하므로, 중복되지 않는 name 속성이 필요하다.
                      // @Table Annotation이 없으면 @Entity에 등록한 name 속성이 테이블 이름이 된다.
                      // @Entity에 등록한 name 속성이 없으면 엔티티 클래스 이름이 테이블 이름이 된다.
/*
 * 엔티티의 특징
 * 1. 디폴트 생성자는 필수로 존재해야 한다.
 * 2. final 클래스, interface, enum, inner 클래스는 엔티티로 사용할 수 없다.
 * 3. 필드에 final 처리할 수 없다.
 */
public class User {
  
  @Id
  @Column(name="user_id")
  private int userId;
  
  @Column(name="user_email")
  private String userEmail;
  
  @Column(name="user_password")
  private String userPassword;
  
  @Column(name="user_phone")
  private String userPhone;
  
  @Column(name="nickname")
  private String nickname;
  
  @Column(name="address")
  private String address;
  
  @Column(name="create_dt")
  private Date createDt;
  
  @Column(name="user_role")
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
