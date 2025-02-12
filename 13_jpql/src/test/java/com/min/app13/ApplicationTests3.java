package com.min.app13;

import java.util.Arrays;
import java.util.List;

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

import com.min.app13.entity3.Menu;

@SpringBootTest
class ApplicationTests3 {
  
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
	void 내부_조인_test() {
	  
	  // 조인 결과는 영속 컨텍스트에 저장되지 않는다.
	  // 카테고리 종류가 총 8개이므로, 8번의 SELECT문이 동작한다.
	  String jpql = "SELECT m FROM menu3 m JOIN m.category c";
	  
	  List<Menu> menuList = entityManager.createQuery(jpql, Menu.class)
	                                     .getResultList();
	  
	  Assertions.assertThat(menuList).isNotEmpty();
	  menuList.forEach(System.out::println);
	  
	  Assertions.assertThat(entityManager.contains(menuList)).isTrue();
	}
	
	@Test
	void 외부_조인_test() {
	  
	  // 메뉴가 없는 카테고리 이름도 조회할 수 있도록 오른쪽 조인을 수행한다.
	  String jpql = "SELECT m.menuName, c.categoryName FROM menu3 m RIGHT JOIN m.category c ORDER BY c.categoryCode";
	  
	  List<Object[]> menuList = entityManager.createQuery(jpql, Object[].class)
	                                         .getResultList();
	  
	  Assertions.assertThat(menuList).isNotEmpty();
	  
	  menuList.forEach(row -> {
	    System.out.println( Arrays.toString(row) );
	  });
	}
	
	@Test
	void 컬렉션_조인_test() {
	  
	  // 데이터베이스에 존재하는 조인 형식은 아니다.
	  // JPA에서 사용하는 용어로 컬렉션을 가지고 있는 엔티티(Category)를 기준으로 조인하는 방식을 의미한다.
	  String jpql = "SELECT m.menuName, c.categoryName FROM category3 c LEFT JOIN c.menuList m";
	  
	  List<Object[]> menuList = entityManager.createQuery(jpql, Object[].class)
        .getResultList();

    Assertions.assertThat(menuList).isNotEmpty();
    
    menuList.forEach(row -> {
      System.out.println( Arrays.toString(row) );
    });
	}
	
	@Test
	void 세타_조인_test() {
	  
	  // Theta Join
	  // 조인 가능한 모든 경우를 조인하는 방식
	  // SQL의 크로스 조인(Cross Join)과 동일한 개념 (두 테이블을 모든 가능한 조합으로 결합하는 SQL 조인 방식)
	  String jpql = "SELECT m.menuName, c.categoryName FROM menu3 m, category3 c"; // 조인 조건이 없는 JPQL
    
	  List<Object[]> menuList = entityManager.createQuery(jpql, Object[].class)
        .getResultList();

    Assertions.assertThat(menuList).isNotEmpty();
    
    menuList.forEach(row -> {
      System.out.println( Arrays.toString(row) );
    });	  
	}
	
	@Test
	void 페치_조인_test() {
	  
	  // 일반 내부 조인으로 실행하면 n + 1 문제가 발생한다. (LAZY Loding으로 성능 저하 현상이 발생됨)
	  // 1. 최초로 내부 조인 SQL 문이 실행된다. 최초 SQL 문에서는 Menu 엔티티의 정보만 조회한다. (1번)
    // 2. 이후부터는 각 Menu 엔티티의 Category 엔티티 정보는 나중에 필요할 때(여기에서는 출력할 때) 다시 조회한다.
	  // 3. 각 Menu 엔티티의 Category 엔티티 정보를 조회할 때, 별도의 SQL문이 최대 Menu 엔티티의 갯수만큼 실행된다. (N번)
    
	  // String jpql = "SELECT m FROM menu3 m JOIN m.category c"; // 일반 내부 조인
	  
	  // 페치 조인을 사용하면 최초 조인 SQL 쿼리문을 실행할 때 Menu와 Category 엔티티 정보를 모두 조회한다.
	  // 따라서, 쿼리 실행 횟수 감소로 인한 성능 향상을 기대할 수 있다. (대부분의 경우, 성능이 향상된다.)
	  String jpql = "SELECT m FROM menu3 m JOIN FETCH m.category c"; 
    
    List<Menu> menuList = entityManager.createQuery(jpql, Menu.class)
                                       .getResultList();
    
    Assertions.assertThat(menuList).isNotEmpty();
    menuList.forEach(System.out::println);
    
    Assertions.assertThat(entityManager.contains(menuList)).isTrue();
	}
	
	
}
