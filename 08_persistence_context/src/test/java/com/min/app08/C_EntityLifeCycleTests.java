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
    
    // 영속성 컨텍스트에 새로만든 newMenu 엔티티를 옮겨 넣음 (insert가 돌지 않음)
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
    
    // 영속성 컨텍스트에 엔티티를 옮겨 넣음 (insert가 돌지 않음)
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
  
  @Test
  void 준영속_detach_test() {
    
    /* 준영속
     * 영속성 컨텍스트에서 보관 중이던 엔티티를 분리해서 보관하는 상태를 의미
     * detach() 메소드를 이용해서 준영속 상태로 만들 수 있다.
     */
    
    
    // 일단, 영속성 컨텍스트에서 menuCode가 20과 21인 엔티티를 조회한다.
    // 현재, 영속성 컨텍스트에 엔티티가 없으므로 관계형 데이터베이스에서 SELECT를 한다.
    // SELECT 결과는 영속성 컨텍스트에 저장한다.
    Menu foundMenu1 = entityManager.find(Menu.class, 20);
    Menu foundMenu2 = entityManager.find(Menu.class, 21);
    
    // foundMenu1을 준영속 상태로 변경한다.
    // 이제 foundMenu1은 영속성 컨텍스트에 존재하지 않는다.
    entityManager.detach(foundMenu1);
    
    // 준영속 상태의 엔티티를 수정한다.
    foundMenu1.setMenuName("앙버터초무침");
    
    // 영속 상태의 엔티티를 수정한다.
    foundMenu2.setMenuName("홍어회스크류바");
    
    // find() : menuCode 20번은 영속성 컨텍스트에 없는 준영속 엔티티이기 때문에 관계형 데이터베이스에서 SELECT 한다.
    // 테스트 실패 (관계형 데이터베이스에서 조회하여 가져오기 때문에, MenuName 수정 불가 !)
    Assertions.assertThat(entityManager.find(Menu.class, 20).getMenuName()).isEqualTo("앙버터초무침");
    
    // find() : menuCode 21번은 영속성 컨텍스트에 있는 영속성 엔티티이기 때문에 해당 엔티티를 가져온다.
    // 테스트 성공
    Assertions.assertThat(entityManager.find(Menu.class, 21).getMenuName()).isEqualTo("홍어회스크류바");    
  }
  
  @Test
  void 준영속_clear_test() {
    
    // 일단, 영속성 컨텍스트에서 menuCode가 20과 21인 엔티티를 조회한다.
    // 현재, 영속성 컨텍스트에 엔티티가 없으므로 관계형 데이터베이스에서 SELECT를 한다.
    // SELECT 결과는 영속성 컨텍스트에 저장한다.
    Menu foundMenu1 = entityManager.find(Menu.class, 20);
    Menu foundMenu2 = entityManager.find(Menu.class, 21);
    
    // clear() : 영속성 컨텍스트를 초기화하는 메소드(일괄적으로 영속 엔티티들을 모두 준영속 상태로 만든다.)
    // foundMenu1, foundMenu2가 모두 준영속 상태가 된다.
    entityManager.clear();
    
    // 준영속 상태의 엔티티를 수정한다.
    foundMenu1.setMenuName("앙버터초무침");
    foundMenu2.setMenuName("홍어회스크류바");
    
    // 영속성 컨텍스트에 menuCode가 20, 21인 엔티티가 없으므로 모두 관계형 데이터베이스에서 SELECT 한다.
    // 모든 테스트 실패
    Assertions.assertThat(entityManager.find(Menu.class, 20)).isEqualTo("앙버터초무침");
    Assertions.assertThat(entityManager.find(Menu.class, 21)).isEqualTo("홍어회스크류바");
  }
  
  @Test
  void 준영속_close_test() {
    
    Menu foundMenu1 = entityManager.find(Menu.class, 20);
    Menu foundMenu2 = entityManager.find(Menu.class, 21);
    
    // close() : 영속성 컨텍스트를 종료한다. 엔티티매니저를 다시 생성해야만 영속성 컨텍스트를 다시 사용할 수 있다.
    // 엔티티매니저 생성 이전에는 IllegalStateException 예외가 발생한다.
    // 영속성 컨텍스트가 종료되어 더이상 사용할 수 없다.
    entityManager.close();

    // 준영속 상태의 엔티티를 수정한다.
    foundMenu1.setMenuName("앙버터초무침");
    foundMenu2.setMenuName("홍어회스크류바");
    
    // find() : 메소드 동작 시 영속성 컨텍스트에 가장 먼저 접근하는데,
    // 현재 영속성 컨텍스트가 닫힌 상태이므로 IllegalStateException 예외가 발생한다.
    Assertions.assertThat(entityManager.find(Menu.class, 20)).isEqualTo("앙버터초무침");
    Assertions.assertThat(entityManager.find(Menu.class, 21)).isEqualTo("홍어회스크류바");
  }
  
  @Test
  void 준영속_merge_test() {
    
    // menuCode 1인 엔티티는 영속성 컨텍스트에 없음으로 DB에서 SELECT해와서 영속성 컨텍스트에 저장한다.
    int menuCode = 1;
    Menu foundMenu = entityManager.find(Menu.class, menuCode);
    
    // detach() : foundMenu를 영속 > 준영속 엔티티로 변경
    entityManager.detach(foundMenu);
    
    // 준영속 상태에 있는 foundMenu 엔티티를 영속성 컨텍스트에 반환한다.
    // mergedMenu는 영속 상태이고, foundMenu는 준영속 상태이다.
    Menu mergedMenu = entityManager.merge(foundMenu);
    
    // foundMenu == margedMenu 는 서로 다른 객체임으로 테스트 실패
    Assertions.assertThat(foundMenu == mergedMenu).isTrue();
    
    // menuCode 1인 엔티티를 영속성 컨텍스트에서 조회한다.
    // 조회 결과, mergedMenu 엔티티가 존재하여 해당 엔티티를 반환한다.
    // entityManager.find(Menu.class, menuCode) == mergedMenu
    Assertions.assertThat(entityManager.find(Menu.class, menuCode) == mergedMenu).isTrue();
  }
  
  @Test
  void 준영속_merge_update_test() {
    
    // menuCode 1인 엔티티는 영속성 컨텍스트에 없음으로 DB에서 SELECT해와서 영속성 컨텍스트에 저장한다.(영속 엔티티)
    // 영속성 컨텍스트의 "1차 캐시"에 foundMenu 엔티티의 정보(@Id, Entity, Snapshot)가 저장된다.
    int menuCode = 1;
    Menu foundMenu = entityManager.find(Menu.class, menuCode);
    
    // foundMenu를 영속 > 준영속 엔티티로 변경
    entityManager.detach(foundMenu);
    
    // 준영속 상태의 foundMenu 엔티티 내용을 수정
    foundMenu.setMenuName("까나리아메리카노");
    
    // merge() 메소드 동작 순서
    // 1. foundMenu 엔티티의 @Id(menuCode = 1) 값으로 영속성 컨텍스트의 "1차 캐시"에서 엔티티를 조회한다.
    //    "1차 캐시"에 없으면 DB에서 조회하고 조회 결과를 "1차 캐시"에 저장한다. DB에서 조회가 안되면, 새로운 영속 엔티티를 생성해서 반환한다.
    // 2. foundMenu 엔티티는 영속성 컨텍스트의 "1차 캐시"에서 조회가 된다. (현재 준영속 엔티티이지만, 영속성 컨텍스트의 1차 캐시에 내용이 남겨져있다.)
    // 3. 영속성 컨텍스트에 넣어져있던 당시 저장된 "1차 캐시"에서 조회한 엔티티 내용과
    //    준영속 엔티티 foundMenu의 값을 병합해 영속 엔티티를 반환한다.
    // ** 결론은 준영속 엔티티였던 mergedMenu가 영속 엔티티로 변한다.
    Menu mergedMenu = entityManager.merge(foundMenu);
    
    // entityManager.find(Menu.class, menuCode) == mergedMenu
    // find() 를 통해 영속성 컨텍스트에서 영속 엔티티를 조회함
    Assertions.assertThat(entityManager.find(Menu.class, menuCode).getMenuName()).isEqualTo("까나리아메리카노");
    Assertions.assertThat(mergedMenu.getMenuName()).isEqualTo("까나리아메리카노");
  }
  
  @Test
  void 준영속_merge_insert_test() {
    int menuCode = 1;
    Menu foundMenu = entityManager.find(Menu.class, menuCode);
    
    // foundMenu 영속 > 준영속으로 변경
    entityManager.detach(foundMenu);
    
    // 준영속 엔티티 내용 변경
    foundMenu.setMenuCode(1000); // 식별자 변경
    foundMenu.setMenuName("시레기라떼");
    
    // foundMenu의 menuCode = 1000을 영속성 컨텍스트의 "1차 캐시"에서 찾는다.
    // "1차 캐시"가 없으므로 DB에서 찾는다.
    // DB에도 없으므로, 새로운 엔티티를 생성하여 foundMenu 엔티티의 내용과 병합하여 반환한다.
    Menu mergedMenu = entityManager.merge(foundMenu);
    
    // mergedMenu의 가격이 4500원이 맞는지 테스트(테스트 통과)
    Assertions.assertThat(mergedMenu.getMenuPrice()).isEqualTo(4500);
  }
  
  @Test
  void 삭제_remove_test() {
    
    // remove() : 영속 상태의 엔티티를 삭제 상태의 엔티티로 변경한다.
    
    // menuCode가 21인 엔티티가 영속성 컨텍스트에 저장된다.
    // "1차 캐시"에 menuCode = 21인 엔티티가 저장된다.
    int menuCode = 21;
    Menu foundMenu = entityManager.find(Menu.class, menuCode);
    
    // 영속 상태의 foundMenu 엔티티가 삭제 상태가 된다.
    // 삭제되어도 영속성 컨텍스트에 넣어져있던 당시에 저장된 1차 캐시의 내용은 남아있다.
    entityManager.remove(foundMenu);
    
    // "1차 캐시"에서 menuCode = 21인 엔티티 정보를 찾아 반환한다.
    // 하지만 foundMenu(삭제) 엔티티와 foundMenu2 엔티티는 서로 다른 상태의 엔티티이다. 
    Menu foundMenu2 = entityManager.find(Menu.class, menuCode);
    
    // 테스트 실패
    Assertions.assertThat(foundMenu == foundMenu2).isTrue();
  }
  
  @Test
  void 삭제_persist_test() {
    
    /*
     * persist()
     * 1. 비영속 상태의 엔티티(대표적으로 new)를 영속 상태로 만든다.
     * 2. 삭제 상태의 엔티티를 영속 상태로 만든다.
     */
    
    // 영속 상태의 foundMenu 엔티티가 영속성 컨텍스트에 저장된다.
    int menuCode = 21;
    Menu foundMenu = entityManager.find(Menu.class, menuCode);
    
    // foundMenu를 영속 상태 > 삭제 상태로 변경
    entityManager.remove(foundMenu);
    
    // foundMenu를 삭제 상태 > 영속 상태로 다시 변경
    entityManager.persist(foundMenu);
    
    // 영속성 컨텍스트에 menuCode = 21인 엔티티가 있으므로 해당 엔티티를 반환한다.
    Menu foundMenu2 = entityManager.find(Menu.class, menuCode);
    
    // foundMenu, foundMenu2는 영속 엔티티로 동일하다. (테스트 성공)
    Assertions.assertThat(foundMenu == foundMenu2).isTrue();
  }
  
  
  

}
