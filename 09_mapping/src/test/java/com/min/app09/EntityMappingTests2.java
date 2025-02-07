package com.min.app09;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.min.app09.entity2.User;

@SpringBootTest
class EntityMappingTests2 {
  
   //엔티티 매니저 팩토리 선언 (싱글톤으로 생성 _ static)
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
   // 엔티티 매니저는 엔티티 매너지 팩토리를 통해 생성한다.
   @BeforeEach
   void setEntityManager() throws Exception {
     entityManager = entityManagerFactory.createEntityManager();
   }

	@Test
	void 칼럼_테스트() {
	  
	  User user = new User();
	  
	  user.setUserEmail("admin@admin.com");
	  user.setUserPassword("admin");
	  user.setUserPhone("010-1234-5678");
	  user.setNickname("관리자");
	  user.setAddress("강남구 역삼동");
	  user.setCreateDt(new Date());
	  user.setUserRole("ADMIN");
	  
	  // 엔티티 트랜잭션 생성
	  EntityTransaction entityTransaction = entityManager.getTransaction();
	  
	  // 트랜잭션 시작
	  entityTransaction.begin();
	  
	  try {
	    
	    // 엔티티 추가
	    entityManager.persist(user);
	    
	    // 커밋
	    entityTransaction.commit();  
	    
    } catch (Exception e) {
      e.printStackTrace();
      
      // 롤백
      entityTransaction.rollback();
    }
	  
	  String jpql = "SELECT u.userId FROM user2 u"; // user2 엔티티의 u.userId 필드를 조회 (SELECT 필드 FROM 엔티티)
	  List<Integer> userIds = entityManager.createQuery(jpql, Integer.class).getResultList();
	  System.out.println(userIds);
	  userIds.forEach(userId -> System.out.println(userId));
	  userIds.forEach(System.out::println);
	
	}

}
