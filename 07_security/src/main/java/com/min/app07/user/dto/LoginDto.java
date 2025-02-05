package com.min.app07.user.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.min.app07.common.UserRole;

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
public class LoginDto {
  
  private int userNo;
  private String userId;
  private String userPassword;
  private String userName;
  private UserRole userRole;
  
  // ArrayList 반환
  public List<String> getUserRoles() {
    if(userRole.getUserRole().length() > 0) {
      return Arrays.asList(userRole.getUserRole().split(",")); 
    }
    return new ArrayList<>(); // 없으면 비어 있는 ArrayList 반환
  }
  
}
