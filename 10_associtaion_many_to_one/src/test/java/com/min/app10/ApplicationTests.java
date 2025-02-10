package com.min.app10;

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

import com.min.app10.entity.Category;
import com.min.app10.entity.Menu;

@SpringBootTest
class ApplicationTests {

  // 엔티티 매니저 팩토리
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
  
  // 개별 테스트가 종료될때마다 엔티티 매니저를 소멸합니다. (테스트 메소드가 동작한 이후)
  @AfterEach
  void closeEntityMananger() throws Exception {
    entityManager.close();
  }
  
	@Test
	void 다대일_객체_그래프_탐색_select_test() {
	  
	  // 조회할 menuCode
	  int menuCode = 10;
	  
	  // 조회
	  Menu foundMenu = entityManager.find(Menu.class, menuCode);
	  
	  // 조회 결과 확인
	  System.out.println(foundMenu); 
	}
	
	@Test
	void 다대일_JPQL_select_test() {
	  
	  // 엔티티간의 join (menuCode = 10의 categoryName 조회 == 서양)
	  String jpql = "SELECT c.categoryName FROM menu m JOIN m.category c WHERE m.menuCode = 10";
	  
	  String categoryName = entityManager.createQuery(jpql, String.class).getSingleResult();
	  
	  Assertions.assertThat(categoryName).isEqualTo("서양");
	}
	
	@Test
	void 다대일_객체_insert_test() {
	  
	  // 삽입할 Category 엔티티
	  Category category = new Category();
	  category.setCategoryCode(20); // 기존 1~12번은 피하고 신규 CategoryCode를 만든다.
	  category.setCategoryName("신규");
	  category.setRefCategoryCode(null);
	  
	  // 삽입할 Menu 엔티티
	  Menu menu = new Menu();
	  menu.setMenuCode(100); // 기존 1~21번은 피하고 신규 CategoryCode를 만든다.
	  menu.setMenuName("죽방멸치빙수");
	  menu.setMenuPrice(30000);
	  menu.setCategory(category);
	  menu.setOrderableStatus("Y");
	  
	  // 엔티티 트랙잭션 생성
	  EntityTransaction entityTransaction = entityManager.getTransaction();
	  
	  // 트랜잭션 시작
	  entityTransaction.begin();
	  
	  try {
      
	    // persist() : 엔티티를 영속 컨텍스트에 저장
	    entityManager.persist(menu);
	    
	    // 커밋 : 영속 컨텍스트의 엔티티를 db에 반영
	    entityTransaction.commit();
	    
    } catch (Exception e) {
      // 예외 출력
      e.printStackTrace();
      
      // 롤백
      entityTransaction.rollback();
    }
	  // 삽입된 엔티티 조회 : 삽입된 엔티티의 key값을 이용해서 조회한다.
	  Menu foundMenu = entityManager.find(Menu.class, 100);
	  Assertions.assertThat(foundMenu.getMenuName()).isEqualTo("죽방멸치빙수");
	}

}