package com.min.app09;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.min.app09.entity3.User;
import com.min.app09.entity3.UserRole;

@SpringBootTest
class EntityMappingTests3 {
  
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
	  
	  user.setUserId(1);
	  user.setUserEmail("admin@admin.com");
	  user.setUserPassword("admin");
	  user.setUserPhone("010-1234-5678");
	  user.setNickname("관리자");
	  user.setAddress("강남구 역삼동");
	  user.setCreateDt(new Date());
	  user.setUserRole(UserRole.ADMIN); // == 0, Enum 사용을 위해 정확한 타입을 명시해줘야 함
	  
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
	  
	  // @Enumerated(EnumType.ORDINAL) 인 경우, UserRole은 INTEGER 타입이다.
	  // @Enumerated(EnumType.STRING) 인 경우, UserRole은 VARCHAR 타입이다.
	  // User foundUser = entityManager.find(User.class, 1);
	  // out.println(foundUser);
	
	  // user3 엔티티에서 u.nickname라는 엔티티의 필드를 조회하는 방식
	  // nickname 필드는 @Access(AccessType.PROPERTY)로 설정되어 있으므로 getNickName()을 이용해서 조회가 된다. (* user3 엔티티 클래스 참고)
	  String jpql = "SELECT u.nickname FROM user3 u WHERE u.userId = 1";
	  String nickname = entityManager.createQuery(jpql, String.class).getSingleResult();
	  Assertions.assertThat(nickname).isEqualTo("관리자님");
	}

}
