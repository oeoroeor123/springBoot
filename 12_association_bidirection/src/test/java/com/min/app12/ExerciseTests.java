package com.min.app12;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.min.app12.entity.exercise.Locker;
import com.min.app12.entity.exercise.Member;
import com.min.app12.entity.exercise.Team;

@SpringBootTest
class ExerciseTests {
    
   //엔티티 매니저 팩토리
   private static EntityManagerFactory entityManagerFactory;
   
   // 엔티티 매니저
   private EntityManager entityManager;
   
   // 전체 테스트를 시작하기 전에 엔티티 매니저 팩토리를 생성합니다. (테스트 클래스가 동작하기 이전)
   @BeforeAll
   static void setEntityManagerFactory() throws Exception {
     entityManagerFactory = Persistence.createEntityManagerFactory("jpa_exercise");
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

	@Test
	void insert_test() {
	  
	  // 1개 팀
	  Team team = new Team();
	  team.setTeamName("1팀");

	  // 5개 락커를 리스트로 만든다.
	  // locker_id는 @GeneratedValue(strategy = GenerationType.IDENTITY) 로 자동 생성된다.
	  List<Locker> lockers = new ArrayList<>();
	  lockers.add(new Locker(null, "A001"));
	  lockers.add(new Locker(null, "A002"));
	  lockers.add(new Locker(null, "A003"));
	  lockers.add(new Locker(null, "A004"));
	  lockers.add(new Locker(null, "A005"));
	  
	  // A004, A005의 경우 persist 코드가 없어 영속 컨텍스트에 없는 상태이다.
	  // persist로 물고 들어가는 locker가 아니기 때문에 아래 코드로 넣어준다.
	  for (Locker locker : lockers) {
	    entityManager.persist(locker);
	  }
	  
	  // 3명 멤버
	  Member member1 = new Member(null, "차범근", team, lockers.get(0));
	  Member member2 = new Member(null, "이종범", team, lockers.get(1));
	  Member member3 = new Member(null, "서장훈", team, lockers.get(2));
	  
	  EntityTransaction entityTransaction = entityManager.getTransaction();
	  
	  entityTransaction.begin();
      
	  entityManager.persist(member1);
	  entityManager.persist(member2);
	  entityManager.persist(member3);
	    
	  entityTransaction.commit();
	  
	  Member foundMember = entityManager.find(Member.class, 1);
	  
	  System.out.println(foundMember);
	}
	
	@Test
	void select_test() {
	  
	  // Member와 Locker의 join 쿼리문 실행 (Member 와 Locker 는 FetchType.EAGER)
	  Member foundMember = entityManager.find(Member.class, 1);
	  
	  // Team 정보를 가져오는 쿼리문 실행 (Member 와 Team 은 FetchType.LAZY)
	  System.out.println(foundMember);
	}
	

}
