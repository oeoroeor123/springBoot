package com.min.app15.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.min.app15.model.dto.MenuDto;
import com.min.app15.model.entity.Menu;

/*
 * Spring Data JPA 의 Repository 인터페이스 구조
 * 
 *  Repository                      별도 기능 없음
 *      ↑
 *  CrudRepository                  CRUD 기능 제공
 *      ↑
 *  PagingAndSortingRepository      페이징 기능 제공
 *      ↑
 *  JpaRepository                   영속 컨텍스트 관련 일부 JPA 관련 추가 기능 제공(ex_삭제)
 * 
 */

// JpaRepository<엔티티, 엔티티ID>, 위 레파지토리 기능등을 모두 내포하고 있음
public interface MenuRepository extends JpaRepository<Menu, Integer> {
  
  List<Menu> findByMenuPriceGreaterThanEqual(Integer menuPrice);
  List<Menu> findByMenuPriceGreaterThanEqual(Integer menuPrice, Sort sort);
  List<Menu> findByMenuPriceGreaterThanEqualOrderByMenuPriceDesc(Integer menuPrice);
  List<Menu> findByMenuNameContaining(String menuName);
}
