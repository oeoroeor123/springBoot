package com.min.app16.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.min.app16.model.dto.BlogDto;

public interface BlogService {
  List<BlogDto> findBlogList(Pageable pageable);
  BlogDto registBlog(BlogDto blogDto);
  BlogDto findBlogById(Integer id);
  BlogDto modifyBlog(BlogDto blogDto);
  void deleteBlogById(Integer id);
}
