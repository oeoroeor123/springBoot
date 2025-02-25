package com.min.app16.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.min.app16.domain.Blog;

public interface BlogRepository extends JpaRepository<Blog, Integer>{

}
