package com.min.app09;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.min.app09.entity1.User;

@SpringBootTest
class EntityMappingTests1 {
  
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
	void 테이블_생성_테스트() {
	  
	  // 영속 엔티티 생성은 테이블의 생성을 의미한다.
	  User user = new User(); // 디폴트 생성자 호출로 테이블이 만들어진다
	  user.setUserId(1);
	  user.setUserEmail("admin@admin.com");
	  user.setUserPassword("admin");
	  user.setUserPhone("010-1234-5678");
	  user.setNickname("관리자");
	  user.setAddress("강남구 역삼동");
	  user.setCreateDt(new Date());
	  user.setUserRole("ADMIN");	
	}

}
