package com.min.app15.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * Repository 를 이용해서 조회된 각종 Entity 정보를 그대로 컨트롤러로 넘기면
 * 의도치 않는 Entity 의 변화로 인해서 해당 변경 사항이 데이터베이스로 flush(=commit) 될 우려가 있다.
 * 따라서, 컨트롤러로 반환할 객체는 비영속 객체인 별도의 DTO를 이용한다.
 * 
 * Controller ← Service ← Repository ← Database
 *           DTO      Entity         Row
 *           
 * Dto와 Entity를 상호 변환하는 과정은 귀찮은 과정이므로,
 * ModelMapper 라이브러리의 도움을 받는다. 필드 이름을 기준으로 상호 변환된다.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MenuDto {

  private Integer menuCode;
  
  private String menuName;
  
  private Integer menuPrice;
  
  private Integer categoryCode;
  
  private String orderableStatus;
  
  
}
