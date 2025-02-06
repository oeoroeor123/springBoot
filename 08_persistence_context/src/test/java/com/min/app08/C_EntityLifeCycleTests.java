package com.min.app08;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
class C_EntityLifeCycleTests {

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
  void 비영속_test() {
     
    // 새로 만든 엔티티는 영속성 컨텍스트에 저장되지 않는다.
    // 즉, newMenu는 비영속 엔티티이다.
    /*
                        영속                                       비영속
      ┌---------- Persistence Context ----------┐   ┌---------- new/transient ----------┐  
      │                                         │   │                                   │
      │                                         │   │              newMenu              │
      │                                         │   │                                   │
      └-----------------------------------------┘   └-----------------------------------┘
     */
    Menu newMenu = new Menu();
    newMenu.setMenuCode(1);
    newMenu.setMenuName("열무김치라떼");
    newMenu.setMenuPrice(4500);
    newMenu.setCategoryCode(8);
    newMenu.setOrderableStatus("Y");
    
    // find() 메소드를 이용해서 조회한 엔티티는 영속성 컨텍스트에 저장된다.
    // 즉, foundMenu는 영속 엔티티이다.
    /*
                       영속                                       비영속
    ┌---------- Persistence Context ----------┐   ┌---------- new/transient ----------┐  
    │                                         │   │                                   │
    │               foundMenu                 │   │              newMenu              │
    │                                         │   │                                   │
    └-----------------------------------------┘   └-----------------------------------┘
    */
    int menuCode = 1;
    Menu foundMenu = entityManager.find(Menu.class, menuCode);
    
    // 비영속 엔티티(newMenu)와 영속 엔티티(foundMenu)는 서로 다른 객체이기 때문에 둘은 같지 않다.
    // 두 객체는 참조 값이 다르다. (테스트 결과: fail)
    Assertions.assertThat(newMenu == foundMenu).isTrue();
    
    // 최종 메모리 구조
    //
    //          ┌--------------┐
    //          │ menuCode==1  │0x10000000  :  비영속 엔티티
    //          │ ...          │
    //          │--------------│
    //          │       .      │
    //          │       .      │
    //          │--------------│
    //          │ menuCode==1  │0x20000000  :  영속 컨텍스트에 저장된 영속 엔티티
    //          │ ...          │
    //          │--------------│
    //          │       .      │
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //          │       .      │
    //          │--------------│
    //   newMenu│  0x10000000  │
    //          │--------------│
    //          │       .      │
    //          │       .      │
    //          │--------------│
    // foundMenu│  0x20000000  │
    //          │--------------│
    //          │       .      │
    //          │       .      │
    //          └--------------┘
    //
  }
  
  @Test
  void 영속_test() {
    
    // 조회할 식별자
    int menuCode = 1;
    
    // 엔티티 조회1
    // 먼저, 영속성 컨텍스트에 menuCode가 1인 엔티티가 존재하는지 조회한다.
    // 없으면, 데이터베이스에서 조회해온다. (SELECT문이 실행된다.)
    // 조회한 엔티티는 영속성 컨텍스트에 저장된다.
    /*
                      영속                               관계형 데이터베이스
    ┌---------- Persistence Context ----------┐   ┌-----------------------------------┐  
    │                                         │   │                                   │
    │                                         │<--│        SELECT FROM WHERE          │
    │                                         │   │                                   │
    └-----------------------------------------┘   └-----------------------------------┘
    */
    Menu foundMenu1 = entityManager.find(Menu.class, menuCode);
    
    // 엔티티 조회2
    // 먼저, 영속성 컨텍스트에 menuCode가 1인 엔티티가 존재하는지 조회한다.
    // 있으므로, 해당 엔티티를 사용한다. (SELECT문이 실행되지 않는다.)
    /*
                      영속
    ┌---------- Persistence Context ----------┐  
    │                                         │
    │       foundMenu1(menuCode == 1)         │
    │                                         │
    └-----------------------------------------┘
    */
    
    Menu foundMenu2 = entityManager.find(Menu.class, menuCode);
    
    // 두 엔티티 객체 비교 (객체는 총 1개로, find를 두 번 썼지만 select가 한 번 돌았음)
    // 두 엔티티가 가지고 있는 참조 값이 동일하기 때문에 테스트 결과는 true임
    System.out.println(foundMenu1.hashCode());
    System.out.println(foundMenu2.hashCode());
    Assertions.assertThat(foundMenu1 == foundMenu2).isTrue();
    
    // 최종 메모리 구조
    //
    //          ┌--------------┐
    //          │ menuCode==1  │0x10000000  :  영속 컨텍스트에 저장된 엔티티
    //          │ ...          │
    //          │--------------│
    //          │       .      │
    //          │       .      │
    //          │--------------│
    //          │       .      │
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //          │       .      │
    //          │--------------│
    //foundMenu1│  0x10000000  │
    //          │--------------│
    //          │       .      │
    //          │       .      │
    //          │--------------│
    //foundMenu2│  0x10000000  │
    //          │--------------│
    //          │       .      │
    //          │       .      │
    //          └--------------┘
    //
  }
	
  @Test
  void 영속_추가_test() {
    
    // 테스트를 위해서 AUTO_INCREMENT 동작을 잠시 중지하고 진행한다. (Menu 엔티티에서 @GeneratedValue를 잠시 주석 처리한다.)
    
    // 영속성 컨텍스트에 추가할 엔티티 생성 (비영속 엔티티)
    /*
                       영속                                       비영속
    ┌---------- Persistence Context ----------┐   ┌---------- new/transient ----------┐  
    │                                         │   │                                   │
    │                                         │   │              newMenu              │
    │                                         │   │                                   │
    └-----------------------------------------┘   └-----------------------------------┘
    */
    
    Menu newMenu = new Menu();
    newMenu.setMenuCode(999); // 위에서 AUTO_INCREMENT 동작 중지 후 별도로 999번 키 값을 부여
    newMenu.setMenuName("갈치파르페");
    newMenu.setMenuPrice(10000);
    newMenu.setOrderableStatus("Y");
    newMenu.setCategoryCode(7);
    
    // 영속성 컨텍스트에 엔티티 추가 (insert가 돌지 않음)
    /*
                      영속                                       비영속
    ┌---------- Persistence Context ----------┐   ┌---------- new/transient ----------┐  
    │                                         │   │                                   │
    │         newMenu (menuCode == 999)       │   │                                   │
    │                                         │   │                                   │
    └-----------------------------------------┘   └-----------------------------------┘
    */    
    entityManager.persist(newMenu);
    
    // 엔티티 조회
    // 먼저, 영속성 컨텍스트에서 menuCode가 999인 엔티티가 존재하는지 조회한다.
    // 이미 menuCode가 999인 엔티티가 영속성 컨텍스트에 저장되어있어 select가 돌지 않음
    // 있으므로, 해당 엔티티를 사용한다. 
    int menuCode = 999;
    Menu foundMenu = entityManager.find(Menu.class, menuCode);
    
    // 두 엔티티 비교하기
    Assertions.assertThat(newMenu == foundMenu).isTrue();
  }
  
  @Test
  void 영속_수정_test() {
    
    // 테스트를 위해서 AUTO_INCREMENT 동작을 잠시 중지하고 진행한다. (Menu 엔티티에서 @GeneratedValue를 잠시 주석 처리한다.)
    
    // 영속성 컨텍스트에 추가할 엔티티 생성 (비영속 엔티티)
    /*
                      영속                                       비영속
    ┌---------- Persistence Context ----------┐   ┌---------- new/transient ----------┐  
    │                                         │   │                                   │
    │                                         │   │              newMenu              │
    │                                         │   │                                   │
    └-----------------------------------------┘   └-----------------------------------┘
    */
    Menu newMenu = new Menu();
    newMenu.setMenuCode(999);
    newMenu.setMenuName("갈치파르페");
    newMenu.setMenuPrice(10000);
    newMenu.setOrderableStatus("Y");
    newMenu.setCategoryCode(7);
    
    // 영속성 컨텍스트에 엔티티 저장 (insert가 돌지 않음)
    /*
                     영속                                       비영속
    ┌---------- Persistence Context ----------┐   ┌---------- new/transient ----------┐  
    │                                         │   │                                   │
    │      newMenu (menuCode == 999)          │   │                                   │
    │              (menuName == "갈치파르페") │   │                                   │
    └-----------------------------------------┘   └-----------------------------------┘
    */   
    entityManager.persist(newMenu);
    
    // 영속성 컨텍스트의 엔티티를 수정
    /*
                      영속                                       비영속
    ┌---------- Persistence Context ----------┐   ┌---------- new/transient ----------┐  
    │                                         │   │                                   │
    │      newMenu (menuCode == 999)          │   │                                   │
    │              (menuName == "우럭마카롱") │   │                                   │
    └-----------------------------------------┘   └-----------------------------------┘
    */
    newMenu.setMenuName("우럭마카롱");
    
    // 영속 엔티티 (foundMenu = newMenu의 데이터) 조회
    // 일단, 영속성 컨텍스트에서 munuCode가 999인 엔티티가 존재하는지 조회
    // 있으므로, 해당 엔티티를 사용한다. (select가 돌지 않음)
    
    /* 
                      영속                                       비영속
    ┌---------- Persistence Context ----------┐   ┌---------- new/transient ----------┐  
    │                                         │   │                                   │
    │      newMenu (menuCode == 999)          │   │                                   │
    │              (menuName == "우럭마카롱") │   │                                   │
    └-----------------------------------------┘   └-----------------------------------┘
    */
    
    Menu foundMenu = entityManager.find(Menu.class, 999);
    
    // munuCode가 999번인 칼럼의 이름 조회 테스트
    Assertions.assertThat(newMenu.getMenuName()).isEqualTo("우럭마카롱");
    Assertions.assertThat(foundMenu.getMenuName()).isEqualTo("우럭마카롱");
  }

}
