package com.min.app07.common;

public enum UserRole {
  /* 
   * ADMIN("ADMIN"), USER("USER");
   * int 타입을 mysql에서 지정한 varchar(5)로 변경하기 위해 ("ADMIN")을 저장한다.
   * UserRole.ADMIN == 0;
   * userRole.USER == 1;
   */
  ADMIN("ADMIN"), USER("USER");
  
  private String userRole;
  
  // getter
  public String getUserRole() {
    return userRole;
  }

  // setter
  public void setUserRole(String userRole) {
    this.userRole = userRole;
  }

  UserRole(String userRole) {
    this.userRole = userRole;
  }

  @Override
  public String toString() {
    return "UserRole[userRole=" + userRole + "]";
    
  }
}
