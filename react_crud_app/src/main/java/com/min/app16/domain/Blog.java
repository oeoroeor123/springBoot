package com.min.app16.domain;

import java.time.LocalDateTime;

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

@Entity(name = "blog")
@Table(name = "tbl_blog")
public class Blog {
  // title, content만 내용을 넣으면 됨. 나머지는 자동생성 되게끔 처리
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String title;
  private String content;
  @Column(name = "create_dt", columnDefinition = "DATETIME DEFAULT NOW()", insertable = false) // 삽입하지 않아도 자동으로 현재 시간이 삽입된다.
  private LocalDateTime createDt;
}
