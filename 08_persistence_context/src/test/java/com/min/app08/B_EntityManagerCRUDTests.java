package com.min.app08;

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

import com.min.app08.entity.Menu;

@SpringBootTest
class B_EntityManagerCRUDTests {

  // 엔티티 매니저 팩토리 선언 (싱글톤으로 생성 _ static)
  private static EntityManagerFactory entityManagerFactory;
  
  // 엔티티 매니저 선언 (요청할 때 마다 생성, 소멸됨)
  private EntityManager entityManager;
  
  // 전체 테스트를 시작하기 전에 '엔티티 매니저 팩토리'를 생성한다. (테스트 클래스(현재 파일)가 동작하기 전에 먼저 실행되는 코드)
  // 엔티티 매니저 팩토리 생성 메소드 (싱글톤으로 static 붙여야 함)
  @BeforeAll
  static void setEntityManagerFactory() throws Exception {
    entityManagerFactory = Persistence.createEntityManagerFactory("jpa_test");
  }
  
  // 개별 테스트를 시작하기 전에 '엔티티 매니저'를 생성한다. (테스트 메소드가 동작하기 전에 먼저 실행되는 코드)
  @BeforeEach
  void setEntityManager() throws Exception {
    entityManager = entityManagerFactory.createEntityManager();
  }
  
  // 전체 테스트가 종료되면 '엔티티 매니저 팩토리'를 소멸한다. (테스트 클래스가 동작한 이후에 실행되는 코드)
  // (싱글톤으로 static 붙여야 함)
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
	void insert_test() {
	  
	  // persist: insert 가 돈다.
	  
	  // 엔티티 생성
	  Menu menu = new Menu();
	  menu.setMenuName("시그니처 초콜렛");
	  menu.setMenuPrice(5500);
	  menu.setOrderableStatus("Y");
	  menu.setCategoryCode(1);
	  
	  // 엔티티 매니저(entityManager)의 트랜잭션 처리를 위한 EntityTransaction 인스턴스 생성
	  EntityTransaction entityTransaction = entityManager.getTransaction();
	  
	  // 트랜잭션 시작
	  entityTransaction.begin();
	  
	  // 예외처리
	  try {
	    
	    // 영속성 컨텍스트에 엔티티를 저장(insert)
	    entityManager.persist(menu);
	    
	    // 커밋 (EntityManager가 관리하는 모든 엔티티가 관계형 데이터베이스에 반영된다. 관계형 데이터베이스에 쿼리를 전송하여 엔티티를 추가하는 시점이다.)
	    entityTransaction.commit();
      
    } catch (Exception e) {
      
      // 예외 추적
      e.printStackTrace();
      
      // 롤백 (EntityManager가 관리하는 모든 엔티티가 이전 상태로 되돌아간다. 롤백 이후 다시 begin() 메소드를 호출하여 트랜잭션을 다시 시작할 수 있다.)
      entityTransaction.rollback();
    }
	  
	  // 테스트 (엔티티 매니저가 .contains(menu)로 menu 엔티티를 가지고 있는지 .isTrue()로 체크)
	  Assertions.assertThat(entityManager.contains(menu)).isTrue();	  
	}
	
	 @Test
	  void select_test() {
	   
	   // find : select가 돈다.
	   
	   // 조회할 식별자 선언
	   int menuCode = 1;
	   
	   // 엔티티 매니저를 이용해 엔티티 조회 시, 식별자(아이디를 선언한 칼럼 ex_menuCode)를 이용한다. 없으면 null이 조회된다. (select)
	   Menu foundMenu = entityManager.find(Menu.class, menuCode);	   
	   
	   // 조회한 엔티티 출력
	   System.out.println(foundMenu);
	   
	   // 존재하는지 테스트
	   Assertions.assertThat(foundMenu).isNotNull();
	   // Assertions.assertThat(foundMenu.getMenuName()).isEqualTo("시그니처 초콜렛");
	  }
	 
	  @Test
	  void update_test() {
	    
	    // update는 따로 도는 코드가 없고 엔티티를 변경함
	    
	    // 수정하고자 하는 엔티티를 조회
	    int menuCode = 1;
	    Menu foundMenu = entityManager.find(Menu.class, menuCode);
	    
	    // EntityTransaction 객체 생성
	    EntityTransaction entityTransaction = entityManager.getTransaction();
	    
	    // 트랜잭션 시작
	    entityTransaction.begin();
	    
	    // 수정할 내용 선언 (메뉴 이름)
	    String menuName = "물김치라떼";

	    try {
	      // Dirty Checking
	      // 연속성 컨텍스트에 저장된 엔티티 상태가 변경되면, 커밋 시점에 자동으로 해당 변경 내용이 관계형 데이터베이스에 반영되는 것을 의미
	      // 정리) 영속 상태인 엔티티가 커밋 시점에 관계형 데이터베이스에 적용된다.
	      // 수정 코드가 따로 없이 setter 호출로 변경 내용을 새롭게 저장함 (setMenuName();)
	      // 최초 변경 후 같은 내용으로 다시 MenuName을 변경하면 변경된 내용이 있을 경우에만 동작하는 Dirty Checking 특징에 따라 setter가 돌지 않음 (변경 안됨)
	      // 내용 수정 위치
	      foundMenu.setMenuName(menuName);
	      
	      // 커밋 (엔티티의 변경 사항이 관계형 데이터베이스에 반영된다.)
	      entityTransaction.commit();

      } catch (Exception e) {
        
        // 예외 추적
        e.printStackTrace();
        
        // 롤백
        entityTransaction.rollback();
      }
	    
	    // 수정된 내용 확인하기
	    Assertions.assertThat(entityManager.find(Menu.class, menuCode).getMenuName()).isEqualTo(menuName);  
	  }
	  
	   @Test
	    void delete_test() {
	     
	     // remove : delete가 돈다.
	     
	     // 삭제하고자 하는 엔티티(foundMenu)를 조회 > select 가동
	     int menuCode = 1;
	     Menu foundMenu = entityManager.find(Menu.class, menuCode);
	     
	     // EntityTransaction 객체 생성
	     EntityTransaction entityTransaction = entityManager.getTransaction();
	     
	     // 트랜잭션 시작
	     entityTransaction.begin();
	     
	     // 예외 처리
	     try {
        
	       // 영속성 컨텍스트에 있는 엔티티(foundMenu)를 삭제(removed) 상태로 변경 (delete)
	       entityManager.remove(foundMenu);
	       
	       // 커밋 (관계형 데이터베이스에 쿼리문을 전송하여 엔티티를 삭제한다.)
	       entityTransaction.commit();
	       
      } catch (Exception e) {
        
        // 예외 추적
        e.printStackTrace();
        
        // 롤백
        entityTransaction.rollback();
      }
	     // 엔티티가 삭제되었는지 테스트 (menuCode가 삭제되어 null 값이 맞는지 체크)
	     Assertions.assertThat(entityManager.find(Menu.class, menuCode)).isNull();
	    }

}
