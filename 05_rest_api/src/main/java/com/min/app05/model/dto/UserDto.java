package com.min.app05.model.dto;

import java.sql.Timestamp;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class UserDto {
  private int userId;
  
  // 유효성 검사 (defaultMessage와 code로 오류 메시지를 구분함)
  
  // @NotNull(message = "이메일은 반드시 입력해야 합니다.") // null 체크만 가능
  @NotBlank(message = "이메일은 반드시 입력해야 합니다.")
  @Size(max=100, message="이메일의 최대 글자수는 100자 입니다.")
  private String email;        // NOT NULL
  
  @Size(min=4, max=20, message="비밀번호는 4 ~ 20자 입니다.")
  private String pwd;
  
  @NotBlank(message = "닉네임은 반드시 입력해야 합니다.")
  @Size(max=100, message="닉네임의 최대 글자수는 100자 입니다.")
  private String nickname;     // NOT NULL
  
  // @Past : 현재 보다 과거여야 한다. (생산일자)
  // @Future : 현재 보다 미래여야 한다. (유통기한)
  private Timestamp createDt;
}
