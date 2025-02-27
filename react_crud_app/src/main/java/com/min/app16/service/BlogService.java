package com.min.app16.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.min.app16.model.dto.BlogDto;

public interface BlogService {
  Map<String, Object> findBlogList(Pageable pageable);
  BlogDto registBlog(BlogDto blogDto);
  BlogDto findBlogById(Integer id);
  BlogDto modifyBlog(BlogDto blogDto);
  void deleteBlogById(Integer id);
}
