package com.min.app08;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class A_EntityManagerLifeCycleTests {

  // 엔티티 매니저 팩토리 선언 (싱글톤으로 생성 _ static)
  private static EntityManagerFactory emf;
  
  // 엔티티 매니저 선언 (요청할 때 마다 생성, 소멸됨)
  private EntityManager em;
  
  // 전체 테스트를 시작하기 전에 '엔티티 매니저 팩토리'를 생성한다. (테스트 클래스(현재 파일)가 동작하기 전에 먼저 실행되는 코드)
  // 엔티티 매니저 팩토리 생성 메소드 (싱글톤으로 static 붙여야 함)
  @BeforeAll
  static void setEntityManagerFactory() throws Exception {
    emf = Persistence.createEntityManagerFactory("jpa_test");
  }
  
  // 개별 테스트를 시작하기 전에 '엔티티 매니저'를 생성한다. (테스트 메소드가 동작하기 전에 먼저 실행되는 코드)
  // 엔티티 매니저는 엔티티 매너지 팩토리를 통해 생성한다.
  @BeforeEach
  void setEntityManager() throws Exception {
    em = emf.createEntityManager();
  }
  
  // 전체 테스트가 종료되면 '엔티티 매니저 팩토리'를 소멸한다. (테스트 클래스가 동작한 이후에 실행되는 코드)
  // (싱글톤으로 static 붙여야 함)
  @AfterAll
  static void closeEntityManagerFactory() throws Exception {
    emf.close();
  }
  
  // 개별 테스트가 종료될 때 마다 '엔티티 매니저'를 소멸한다.  (테스트 메소드가 동작한 이후에 실행되는 코드)
  @AfterEach
  void closeEntityManager() throws Exception {
    em.close();
  }

  // 엔티티 매니저 팩토리는 1개로 값이 모두 동일함 (싱글톤으로 생성)
  // 엔티티 매니저는 요청할 때 마다 생성, 소멸됨으로 값이 모두 다름
	@Test
	void life_cycel_test_1() {
	  // .hashcode() : 객체의 메모리 값을 해시코드로 저장해 객체의 저장 위치를 확인할 수 있다.
	  System.out.println("EntityManagerFactory : " + emf.hashCode());
	  System.out.println("EntityManagerFactory : " + em.hashCode());
	}
	
	 @Test
	  void life_cycel_test_2() {
	    System.out.println("EntityManagerFactory : " + emf.toString());
	    System.out.println("EntityManagerFactory : " + em.toString());
	  }
	 
	  @Test
	  void life_cycel_test_3() {
	    System.out.println("EntityManagerFactory : " + emf.toString());
	    System.out.println("EntityManagerFactory : " + em.toString());
	  }

}
