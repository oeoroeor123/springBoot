package com.min.app11;

import java.util.Arrays;

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

import com.min.app11.entity.Category;
import com.min.app11.entity.Menu;

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
 
 // 개별 테스트가 종료될 때 마다 '엔티티 매니저'를 소멸한다.  (테스트 메소드가 동작한 이후에 실행되는 코드)
 @AfterEach
 void closeEntityManager() throws Exception {
   entityManager.close();
 }
  
	@Test
	void 일대다_객체_그래프_탐색_select_test() {
	  
	  // 조회할 카테고리 코드
	  Integer categoryCode = 8;
	  
	  // 카테고리 조회 - 1 ( @OneToMany(fetch = FetchType.EAGER) )
	  /*
  	  select
          category0_.category_code as category1_0_0_,
          category0_.category_name as category2_0_0_,
          category0_.ref_category_code as ref_cate3_0_0_,
          menulist1_.category_code as category2_1_1_,
          menulist1_.menu_code as menu_cod1_1_1_,
          menulist1_.menu_code as menu_cod1_1_2_,
          menulist1_.category_code as category2_1_2_,
          menulist1_.menu_name as menu_nam3_1_2_,
          menulist1_.menu_price as menu_pri4_1_2_,
          menulist1_.orderable_status as orderabl5_1_2_ 
      from
          tbl_category category0_ 
      left outer join
          tbl_menu menulist1_ 
              on category0_.category_code=menulist1_.category_code 
      where
          category0_.category_code=?
	   */
	  
	  // 카테고리 조회 -2 ( @OneToMany(fetch = FetchType.LAZY) ) - 지연 로딩
	  /*
  	  select
          category0_.category_code as category1_0_0_,
          category0_.category_name as category2_0_0_,
          category0_.ref_category_code as ref_cate3_0_0_ 
      from
          tbl_category category0_ 
      where
          category0_.category_code=?
	   */
	  Category foundCategory = entityManager.find(Category.class, categoryCode);
	  
	  // 8번 카테고리 코드가 있는지 테스트
	  Assertions.assertThat(foundCategory).isNotNull();
	
	  // 카테고리 조회 결과 확인
	  // 지연 로딩 사용하면 이 때 메뉴가 조회된다.
	  /*
  	    select
          menulist0_.category_code as category2_1_0_,
          menulist0_.menu_code as menu_cod1_1_0_,
          menulist0_.menu_code as menu_cod1_1_1_,
          menulist0_.category_code as category2_1_1_,
          menulist0_.menu_name as menu_nam3_1_1_,
          menulist0_.menu_price as menu_pri4_1_1_,
          menulist0_.orderable_status as orderabl5_1_1_ 
      from
          tbl_menu menulist0_ 
      where
          menulist0_.category_code=?
  	*/
	  System.out.println(foundCategory);
	}
	
	@Test
	void 일대다_객체_insert_test() {
	  
	  // 신규 카테고리 1개 엔티티 생성
	  Category category = new Category();
	  category.setCategoryCode(100);
	  category.setCategoryName("신규");
	  category.setRefCategoryCode(null);
	  
	  // 신규 카테고리 속하는 신규 메뉴 2개 엔티티 생성
	  Menu menu1 = new Menu();
	  menu1.setMenuCode(30);
	  menu1.setMenuName("카레");
	  menu1.setMenuPrice(4500);
	  menu1.setCategoryCode(category.getCategoryCode());
	  menu1.setOrderableStatus("Y");
	  
	  Menu menu2 = new Menu();
	  menu2.setMenuCode(31);
	  menu2.setMenuName("우동");
	  menu2.setMenuPrice(5000);
	  menu2.setCategoryCode(category.getCategoryCode());
	  menu2.setOrderableStatus("Y");
	  
	  category.setMenuList(Arrays.asList(menu1, menu2));
	 
	  // 엔티티 트랜잭션 생성
	  EntityTransaction entityTransaction = entityManager.getTransaction();
	  
	  // 트랜잭션 시작
	  entityTransaction.begin();
	  
	  // 예외 처리
	  try {
      
	    entityManager.persist(category);
	    
	    entityTransaction.commit();
	    
    } catch (Exception e) {
      e.printStackTrace();
      
      entityTransaction.rollback();
    }
	  
	 // 등록하는 신규 카테고리 조회
	 Category foundcategory = entityManager.find(Category.class, 100);
	  
	 System.out.println(foundcategory);
	
	}

	
	
	
	
}
