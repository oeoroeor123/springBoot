package com.min.app13;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.min.app13.entity4.Menu;

@SpringBootTest
class ApplicationTests4 {
  
  //엔티티 매니저 팩토리
  private static EntityManagerFactory entityManagerFactory;
  
  // 엔티티 매니저
  private EntityManager entityManager;
  
  // 전체 테스트를 시작하기 전에 엔티티 매니저 팩토리를 생성합니다. (테스트 클래스가 동작하기 이전)
  @BeforeAll
  static void setEntityManagerFactory() throws Exception {
    entityManagerFactory = Persistence.createEntityManagerFactory("jpa_test");
  }
  
  // 개별 테스트를 시작하기 전에 엔티티 매니저를 생성합니다. (테스트 메소드가 동작하기 이전)
  @BeforeEach
  void setEntityManager() throws Exception {
    entityManager = entityManagerFactory.createEntityManager();
  }
  
  // 전체 테스트가 종료되면 엔티티 매니저 팩토리를 소멸합니다. (테스트 클래스가 동작한 이후)
  @AfterAll
  static void closeEntityManagerFactory() throws Exception {
    entityManagerFactory.close();
  }
  
  // 개별 테스트가 종료될 때 마다 '엔티티 매니저'를 소멸한다.  (테스트 메소드가 동작한 이후에 실행되는 코드)
  @AfterEach
  void closeEntityManager() throws Exception {
    entityManager.close();
  }

	@Test
	void 서브쿼리_select_test() {
	  
	  // JPQL에서는 서브쿼리를 SELECT 절과 FROM 절에서는 사용할 수 없다.
	  // JPQL에서는 서브쿼리를 조건절(WHERE, HAVING)에서만 사용할 수 있다.
	  
	  // 문제. 카테고리 이름이 '서양'인 메뉴를 조회하기(카테고리 이름이 '서양'인 카테고리의 카테고리 코드 조회를 서브쿼리로 사용)
	  
	  StringBuilder jpql = new StringBuilder();
	  jpql.append("SELECT m FROM menu4 m");
	  jpql.append(" WHERE m.categoryCode = ");
	  jpql.append("(SELECT c.categoryCode FROM category4 c WHERE c.categoryName = '서양')");
	  
	  // StringBuilder로 만들었기 때문에 뒤에 .toString()를 붙여줌
	  List<Menu> menuList = entityManager.createQuery(jpql.toString(), Menu.class)
	                                     .getResultList();
	  
	  // menuList가 비어있지 않은지 테스트
	  Assertions.assertThat(menuList).isNotEmpty();
	  menuList.forEach(System.out::println);
	}
	
	@Test
	void 동적쿼리_select_test() {
	  
	  // 문제. 메뉴명에 '버터'가 포함되고, 카테고리 코드가 4인 메뉴 조회하기
	  // 단 메뉴명과 카테고리코드는 입력될 수도 있고 입력되지 않을 수도 있다고 가정한다.
	  
	  String searhMenuName = "";
	  int searhCategoryCode = 0;
	  
	  StringBuilder jpql = new StringBuilder("SELECT m FROM menu4 m ");
	  // searhMenuName, searhCategoryCode가 둘다 입력되었을때
	  if(searhMenuName != null && !searhMenuName.isEmpty() && searhCategoryCode > 0) {
	    jpql.append("WHERE ");
	    jpql.append("m.menuName LIKE '%' || :searhMenuName || '%' ");
	    jpql.append("AND ");
	    jpql.append("m.categoryCode = :searhCategoryCode");
	  } else {
	    // searhMenuName만 입력되었을때
	    if(searhMenuName != null && !searhMenuName.isEmpty()) {
	      jpql.append("WHERE ");
	      jpql.append("m.menuName LIKE '%' || :searhMenuName || '%' ");
	      // searhCategoryCode만 입력되었을때
	    } else if(searhCategoryCode > 0) {
	      jpql.append("WHERE ");
	      jpql.append("m.categoryCode = :searhCategoryCode");
	    } 
	  }
	  
	  // TypedQuery 생성
	  TypedQuery<Menu> typedQuery = entityManager.createQuery(jpql.toString(), Menu.class);
	  
	  if(searhMenuName != null && !searhMenuName.isEmpty() && searhCategoryCode > 0) {
	    typedQuery.setParameter("searhMenuName", searhMenuName)
	              .setParameter("searhCategoryCode", searhCategoryCode);
	    
    } else {
      // searhMenuName만 입력되었을때
      if(searhMenuName != null && !searhMenuName.isEmpty()) {
        typedQuery.setParameter("searhMenuName", searhMenuName);
        // searhCategoryCode만 입력되었을때
      } else if(searhCategoryCode > 0) {
        typedQuery.setParameter("searhCategoryCode", searhCategoryCode);
      } 
    }
	  
	  List<Menu> menuList = typedQuery.getResultList();
	  
	  // menuList가 비어있지 않은지 테스트
    Assertions.assertThat(menuList).isNotEmpty();
    menuList.forEach(System.out::println);
    
	  
	  
	}
	
}
