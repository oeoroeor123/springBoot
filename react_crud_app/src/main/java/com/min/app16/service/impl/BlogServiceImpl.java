package com.min.app16.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.min.app16.domain.Blog;
import com.min.app16.model.dto.BlogDto;
import com.min.app16.repository.BlogRepository;
import com.min.app16.service.BlogService;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class BlogServiceImpl implements BlogService {

  private final BlogRepository blogRepository;
  private final ModelMapper modelMapper;
  
  @Transactional(readOnly = true)
  @Override
  public List<BlogDto> findBlogList(Pageable pageable) {
    Page<Blog> blogList = blogRepository.findAll(pageable);
    return blogList.map(blog -> modelMapper.map(blog, BlogDto.class)).toList();
  }

  @Override
  public BlogDto registBlog(BlogDto blogDto) {
    Blog blog = modelMapper.map(blogDto, Blog.class);
    blogRepository.save(blog);
    return blogDto;
  }

  @Transactional(readOnly = true)
  @Override
  public BlogDto findBlogById(Integer id) {
    Blog blog = blogRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    return modelMapper.map(blog, BlogDto.class);
  }

  @Override
  public BlogDto modifyBlog(BlogDto blogDto) {
    Blog foundBlog = blogRepository.findById(blogDto.getId()).orElseThrow(IllegalArgumentException::new); // () -> new IllegalArgumentException()
    foundBlog.setTitle(blogDto.getTitle());
    foundBlog.setContent(blogDto.getContent());
    return blogDto;
  }

  @Override
  public void deleteBlogById(Integer id) {
    blogRepository.deleteById(id);
  }

}
