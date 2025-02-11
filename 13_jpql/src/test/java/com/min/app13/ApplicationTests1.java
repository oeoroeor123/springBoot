package com.min.app13;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.min.app13.entity1.Menu;

@SpringBootTest
class ApplicationTests1 {
  
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
	void TypedQuery_단일_메뉴명_select_test() {
	  
	  String jpql = "SELECT m.menuName FROM menu1 m WHERE m.menuCode = 7";
	  
	  // 반환 타입 : String
	  TypedQuery<String> typedQuery = entityManager.createQuery(jpql, String.class);
	
	  // 결과 행이 1개 일 때, getSingleResult(); 를 사용
	  String menuName = typedQuery.getSingleResult();

	  Assertions.assertThat(menuName).isEqualTo("민트미역국");
	}
	
	@Test
	void Query_단일_메뉴명_selete_test() {
	  
	  String jpql = "SELECT m.menuName FROM menu1 m WHERE m.menuCode = 7";
	  
	    // 반환 타입 명시하지 않고 사용
	    Query query = entityManager.createQuery(jpql);
	    
	    // 반환 타입 명시하지 않은 Query 사용 시, 반환 타입은 Object 또는 Object[]
	    Object menuName = query.getSingleResult();
	    
	    Assertions.assertThat(menuName).isEqualTo("민트미역국");
	}
	
	@Test
	void TypedQuery_다중행_select_test() {
	  
	  // 전체 메뉴(m) select, WHERE은 미사용
	  String jpql = "SELECT m FROM menu1 m";
	  
	  // 반환 타입 : Menu
	  TypedQuery<Menu> typedQuery = entityManager.createQuery(jpql, Menu.class);
	  
	  // 결과 행이 여러개일 때, getResultList()를 사용
	  List<Menu> menuList = typedQuery.getResultList();
	  
	  // 전체 메뉴 리스트 출력
	  menuList.forEach(System.out::println);
	}
	
	@Test
	void Query_다중행_select_test() {
	  
	  String jpql = "SELECT m FROM menu1 m";
	  
	  // 반환 타입 x
	  Query query = entityManager.createQuery(jpql);
	  
	  // 결과 행이 여러개(전체 메뉴 리스트), getResultList()를 사용
	  List<Menu> menuList = query.getResultList();
	  
	  menuList.forEach(System.out::println);
	}

	// 카테고리 코드 조회하기 (중복 제거 = DISTINCT)
	@Test
	void categoryCodes_select_test() {
	  
	  String jpql = "SELECT DISTINCT m.categoryCode FROM menu1 m";	      
	  
	  // categoryCode 칼럼이 Integer 타입이기 때문에 해당 타입으로 작성
	  TypedQuery<Integer> typedQuery = entityManager.createQuery(jpql, Integer.class);
	  
	  List<Integer> categoryCodes = typedQuery.getResultList();
	  
	  categoryCodes.forEach(System.out::println);
	}
	
	// 카테고리 코드가 6 또는 10인 메뉴 조회하기 (IN)
	@Test
	void IN_연산자_select_test() {
	  
	  String jpql = "SELECT m FROM menu1 m WHERE m.categoryCode IN (6,10)";
	  
	  Query query = entityManager.createQuery(jpql);
	  
	  // 카테고리 코드가 6 또는 10인 메뉴가 여러개니까 List에 담고 .getResultList()로 조회
	  List<Menu> menuList = query.getResultList();
	  
	  menuList.forEach(System.out::println);
	}
	
	// 메뉴명에 '마늘'이 포함되는 메뉴 조회하기 (LIKE)
	@Test
	void Query_다중행_메뉴명_마늘_slect_테스트() {
	  
	  String jpql = "SELECT m FROM menu1 m WHERE m.menuName LIKE '%마늘%'";
	 
	  TypedQuery<Menu> typedQuery = entityManager.createQuery(jpql, Menu.class);
	  
	  List<Menu> menuList = typedQuery.getResultList();
	  
	  menuList.forEach(System.out::println);
	}
	
	@Test
	void 이름_기준_parameter_binding_test() {
	  
	  // 콜론(:) 뒤에 파라미터 이름을 붙여서 사용한다.
	  String jpql = "SELECT m FROM menu1 m WHERE m.menuCode = :menuCode";
	
	  Integer menuCode = 8;
	  
	  // 파라미터를 선언하고 setParameter ("파라미터 명", 값)을 부여한다.
	  // 메뉴 코드가 8번인 메뉴 조회
	  TypedQuery<Menu> typedQuery = entityManager.createQuery(jpql, Menu.class)
	                                             .setParameter("menuCode", menuCode);
	  
	  // 결과 행 1개로 .getSingleResult(); 선언
	  Menu menu = typedQuery.getSingleResult();
	  
	  System.out.println(menu);
	}
	
	@Test
	void 위치_기준_parameter_binding_test() {
	  
	  // ? 뒤에 위치 정보를 붙여서 사용한다. 위치 정보의 시작은 1 이다.
	  String jpql = "SELECT m FROM menu1 m WHERE m.categoryCode IN (?1, ?2)";
	  
	  // 카테고리코드가 5 또는 10번인 메뉴 조회
	  TypedQuery<Menu> typedQuery = entityManager.createQuery(jpql, Menu.class)
	                                             .setParameter(1, 5)   // ?1 <--- 5
	                                             .setParameter(2, 10); // ?2 <--- 10
	  
	  // 결과 행이 여러개로 .getResultList(); 선언
	  List<Menu> menuList = typedQuery.getResultList();
	  
	  menuList.forEach(System.out::println);
	}
	
	@Test
	void 페이징_API_select_test() {
	  
	  // 쿼리문에서 페이징 처리를 위한 2가지 변수 (offset, display)
	  int offset = 0;       // 조회 시작할 위치
	  int display = 10;     // 조회할 행의 개수
	  
	  String sort = "DESC"; // 정렬 방식
	  
	  // sort 변수는 JDBC 문법에 따라 마지막에 따로 적어준다.
	  String jpql = "SELECT m FROM menu1 m ORDER BY m.menuCode " + sort;
	  
	  TypedQuery<Menu> typedQuery = entityManager.createQuery(jpql, Menu.class)
	                                             .setFirstResult(offset)
	                                             .setMaxResults(display);
	  
	  List<Menu> menuList = typedQuery.getResultList();
	  menuList.forEach(System.out::println);	  
	}
	
	@Test
	void 통계함수_count_test() {
	  
	  // COUNT()
	  // 1. 반환 타입이 Long 이다.
	  // 2. 데이터가 없는 경우 0을 반환한다.
	  // 3. 반환 타입을 long과 같은 기본 자료형(Primitive Type)을 사용해도 문제 없다.
	  
	  String jpql = "SELECT COUNT(m.menuCode) FROM menu1 m WHERE m.categoryCode = ?1";	  
	  
	  // Integer categoryCode = 10; // 데이터 있음
	  Integer categoryCode = 7; // 데이터 없음 (테스트 실패)
	  
	  // TypedQuery 생략한 코드
	  // 모든 통계 함수는 단일 행으로 작성된다.
	  Long numOfRows = entityManager.createQuery(jpql, Long.class)
	                                .setParameter(1, categoryCode)
	                                .getSingleResult();
	  
	  // 존재하는 categoryCode 호출 시, 테스트 성공
	  Assertions.assertThat(numOfRows > 0).isTrue();
	}
	
	@Test
	void 통계함수_exclude_count_test() {
	  
	  // SUM, AVG, MAX, MIN 함수
	  // 1. 반환 타입이 Long 또는 Double 이다.
	  // 2. 데이터가 없는 경우 NULL을 반환한다.
	  // 3. 반환 타입을 long 또는 double 와 같은 기본 자료형(Primitive Type)을 사용하면 NullPointerException이 발생할 우려가 있습니다.
	  
	  String jpql = "SELECT SUM(m.menuPrice) FROM menu1 m WHERE m.categoryCode = :categoryCode";
	  
	  // 데이터 없음
	  Integer categoryCode = 7;
	  
	  // 2번째 인자값(람다식 함수)의 실행 결과가 테스트 통과이면 NullPointerException이 발생되었다는 뜻이다.
	  org.junit.jupiter.api.Assertions.assertThrows(NullPointerException.class, () -> {
	    
	    // 쿼리문 실행 결과로 NULL이 반환되는데, 이를 long 타입으로 언박싱(UnBoxing)할 때 NullPointerException이 발생한다.
	    long totalPrice = entityManager.createQuery(jpql, Long.class)
                            	       .setParameter("categoryCode", categoryCode)
                            	       .getSingleResult();
	    
	    System.out.println(totalPrice);
	    Assertions.assertThat(totalPrice > 100).isTrue();
	  });	  
	}
	
	@Test
	void 그룹화_select_test() {
	  
	  // 동일한 카테고리 코드 내 N개 이상의 메뉴가 포함된 것만 조회
	  String jpql = "SELECT m.categoryCode, SUM(m.menuPrice) FROM menu1 m GROUP BY m.categoryCode HAVING COUNT(m.menuCode) >= ?1";
	  
	  // HAVING 절에서 사용한 COUNT() 함수의 반환 타입은 Long 이므로 
	  // 이와 비교하는 파라미터의 타입도 Long으로 설정해야 한다.
	  
	  // 동일한 카테고리 코드 내 2개 이상의 메뉴가 포함된 것만 조회
	  Long menuCount = 2L;
	  
	  List<Object[]> categoryPriceList = entityManager.createQuery(jpql, Object[].class)
	                                                  .setParameter(1, menuCount)
	                                                  .getResultList();
	      
	  // 한줄씩 가져온다. : row == Object[] 타입
	  // Arrays.toString(row) : 한줄씩 가져와서 문자열로 반환한다.
	  // 동일한 카테고리 코드 내 2개 이상의 메뉴가 포함된 카테고리 코드, 각 카테고리 코드에 속해 있는 메뉴들의 총 가격
	  categoryPriceList.forEach(row -> {
	    System.out.println( Arrays.toString(row) );
	  });
	  
	}
	
}
