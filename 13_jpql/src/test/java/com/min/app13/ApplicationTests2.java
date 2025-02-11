package com.min.app13;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.min.app13.entity2.CategoryInfo;
import com.min.app13.entity2.Menu;
import com.min.app13.entity2.MenuInfo;

@SpringBootTest
class ApplicationTests2 {
  
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
	void 엔티티_프로젝션_test() {
	  
	  // SELECT 절에서 엔티티를 조회한다.
	  String jpql = "SELECT m FROM menu2 m WHERE m.menuCode = 3";
	  
	  Menu menu = entityManager.createQuery(jpql, Menu.class)
	                           .getSingleResult();
	  
	  Assertions.assertThat(menu).isNotNull();
	  System.out.println(menu);
	  
	  // 엔티티 프로젝션은 조회 결과를 영속 컨텍스트에 저장한다.
	  // 영속 컨텍스트를 관리하는 엔티티 매니저 안에 menu 를 포함하고 있는지 체크한다.
	  Assertions.assertThat(entityManager.contains(menu)).isTrue();
	  	  
	  // orderable_status -> N으로 수정
	  // Dirty Checking : 영속 컨텍스트가 관리하는 엔티티를 수정한 뒤, 커밋하면 변경 사항이 데이터베이스에 적용된다.
	  EntityTransaction entityTransaction = entityManager.getTransaction();
	  
	  entityTransaction.begin();
      
	    menu.setOrderableStatus("N");
	    
	    // 변경 내용 DB에 저장
	    entityTransaction.commit(); 	  
	}
	
	@Test
	void 스칼라_타입_프로젝션_test() {
	  
	  // 전체 카테고리 이름만 조회
	  String jpql = "SELECT c.categoryName FROM category2 c";
	  
	  List<String> categoryNames = entityManager.createQuery(jpql, String.class)
	                                            .getResultList();
	  
	  Assertions.assertThat(categoryNames).isNotEmpty();
	  categoryNames.forEach(System.out::println);
	  
	  // 영속 컨텍스트에 저장되었는지 확인
	  // 스칼라 타입 프로젝션은 영속 컨텍스트에 저장되지 않기 때문에 테스트 실패
	  // Assertions.assertThat(entityManager.contains(categoryNames)).isTrue();	  
	}
	
	@Test
	void 임베디드_프로젝션_test() {
	  
	  // SELECT 절에서 @Embedded 한 객체를 조회 (menu2 엔티티의 menuInfo 칼럼)
	  String jpql = "SELECT m.menuInfo FROM menu2 m WHERE m.menuCode = 5";
	  
	  MenuInfo menuInfo = entityManager.createQuery(jpql, MenuInfo.class)
	                                   .getSingleResult();
	  
	  Assertions.assertThat(menuInfo).isNotNull();
	  System.out.println(menuInfo);
	  
	  // 임베디드 프로젝션의 결과는 영속 컨텍스트에 저장되지 않기 때문에 테스트 실패
	  // select 결과는 console에서 확인 가능, menuInfo 칼럼인 menuName= "", menuPrice="" 결과가 찍힘
	  Assertions.assertThat(entityManager.contains(menuInfo)).isTrue();
	}
	
	@Test
	void new_DTO_프로젝션_test() {
	  
	  // SELECT 절에서 new를 이용해 객체를 생성하고 해당 객체 타입으로 조회
	  String jpql = "SELECT new com.min.app13.entity2.CategoryInfo(c.categoryCode, c.categoryName) FROM category2 c";
	  
	  List<CategoryInfo> categoryInfoList = entityManager.createQuery(jpql, CategoryInfo.class)
	                                                     .getResultList();
	  
	  Assertions.assertThat(categoryInfoList).isNotNull();
	  categoryInfoList.forEach(System.out::println);
	  
	  // new 를 이용해 생성한 객체(new 생성 프로젝션)는 영속 컨텍스트에 저장되지 않기 때문에 테스트는 실패한다.
	  Assertions.assertThat(entityManager.contains(categoryInfoList)).isTrue();
	}
	
}
