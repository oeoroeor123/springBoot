package com.min.app14;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.min.app14.entity.Menu;

@SpringBootTest
class ApplicationTests {
  
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
  
  // 개별 테스트가 종료될 때 마다 '엔티티 매니저'를 소멸한다. (테스트 메소드가 동작한 이후에 실행되는 코드)
  @AfterEach
  void closeEntityManager() throws Exception {
    entityManager.close();
  }

  @SuppressWarnings("unchecked")
	@Test
	void 결과_타입_명시_select_test() {
	  
	  // "결과 타입을 Menu 엔티티나 Category 엔티티로 지정하는 경우" 일부 칼럼만 조회하는 것은 불가능하다. (전체 칼럼 조회만 가능)
	  String sql = "SELECT menu_code, menu_name, menu_price, category_code, orderable_status FROM tbl_menu"; // SELECT * 도 가능하나, 가급적 사용 X
	  
	  Query query = entityManager.createNativeQuery(sql, Menu.class);
	  
	  // 결과 타입 Menu 엔티티
	  List<Menu> menuList = query.getResultList();
	  
	  // 조회 결과 확인
	  Assertions.assertThat(menuList).isNotEmpty();
	  
	  // 영속 컨텍스트에서 관리하는지 확인 (영속 컨텍스트에서 관리하지 않음_테스트 실패)
	  Assertions.assertThat(entityManager.contains(menuList)).isTrue();
	}
  
  @Test
  void 결과_타입_없는_select_test() {
    
    // 결과 타입을 명시하지 않으면 일부 칼럼도 조회할 수 있다. 결과 타입은 Object[]로 지정한다.
    String sql = "SELECT menu_code, menu_name FROM tbl_menu";
    
    // 결과 타입 명시하지 않음, 엔티티 타입으로 결과 타입을 지정할 때만 결과 타입을 명시한다. ex: .createNativeQuery 뒤에 >>> Object[].class 불가
    Query query = entityManager.createNativeQuery(sql);
    
    // 결과 타입은 Object[]
    List<Object[]> menuList = query.getResultList();
    
    // 조회 결과 확인
    Assertions.assertThat(menuList).isNotEmpty();
    menuList.forEach(row -> {
      System.out.println( Arrays.toString(row) );
    });    
  }
  
  @Test
  void 내부_조인_test() {
    
    // 문제. 모든 메뉴의 정보와 추가로 카테고리 이름 정보를 조회하기
    String sql = "SELECT m.menu_code, m.menu_name, m.menu_price, m.orderable_status, c.category_name FROM tbl_category c INNER JOIN tbl_menu m ON m.category_code = c.category_code";
    
    Query query = entityManager.createNativeQuery(sql);
    
    // 결과 타입 지정 x
    List<Object[]> menuList = query.getResultList();
    
    // 조회 결과 확인
    Assertions.assertThat(menuList).isNotEmpty();
    menuList.forEach(row -> {
      System.out.println( Arrays.toString(row) );
    });    
    
  }
  
  @Test
  void 외부_조인_test() {
    
    // 문제. 모든 카테고리 정보를 조회하고, 해당 카테고리에 속한 메뉴의 개수를 함께 조회하기
    // 메뉴가 없는 경우, 0으로 조회한다.
    String sql = "SELECT c.category_code, c.category_name, c.ref_category_code, COUNT(m.menu_code) FROM tbl_category c LEFT OUTER JOIN tbl_menu m ON c.category_code = m.category_code GROUP BY c.category_code, c.category_name, c.ref_category_code";
    
    Query query = entityManager.createNativeQuery(sql);
    
    List<Object[]> menuList = query.getResultList();
    
    /*
     * 선생님 코드
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT c.category_code, c.category_name, c.ref_category_code, v.menu_count");
        sql.append("  FROM tbl_category c");
        sql.append("  LEFT JOIN (SELECT COUNT(*) AS menu_count, m.category_code");
        sql.append("              FROM tbl_menu m");
        sql.append("              GROUP BY m.category_code) v");
        sql.append("    ON c.category_code = v.category_code");
        
        List<Object[]> menuList = entityManager.createNativeQuery(sql.toString())
                                               .getResultList();
     */
    
    // 조회 결과 확인
    Assertions.assertThat(menuList).isNotEmpty();
    menuList.forEach(row -> {
      System.out.println( Arrays.toString(row) );
    });    
    
    
  }

}
