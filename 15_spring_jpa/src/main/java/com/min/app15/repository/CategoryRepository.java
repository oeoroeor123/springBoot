package com.min.app15.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.min.app15.model.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

  // 특정 카테고리 코드 보다 큰 리스트 값만 뽑아낸다.
  List<Category> findByCategoryCodeGreaterThan(Integer categoryCode);
}
