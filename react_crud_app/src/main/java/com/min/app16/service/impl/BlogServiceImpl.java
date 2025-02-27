package com.min.app16.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.min.app16.domain.Blog;
import com.min.app16.model.dto.BlogDto;
import com.min.app16.model.dto.PageDto;
import com.min.app16.repository.BlogRepository;
import com.min.app16.service.BlogService;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class BlogServiceImpl implements BlogService {

  private final BlogRepository blogRepository;
  private final ModelMapper modelMapper;
  private final PageDto pageDto;
  
  @Transactional(readOnly = true)
  @Override
  public Map<String, Object> findBlogList(Pageable pageable) {
    pageDto.setPaging(pageable.getPageNumber(), pageable.getPageSize(), (int)blogRepository.count()); // pageable에 있는 page와 size 빼기, blogRepository에 있는 count 빼기
    pageable = pageable.withPage(pageable.getPageNumber() - 1);
    Page<Blog> blogList = blogRepository.findAll(pageable);
    return Map.of("blogList", blogList.map(blog -> modelMapper.map(blog, BlogDto.class)).toList()
                , "pageList", IntStream.rangeClosed(pageDto.getBeginPage(), pageDto.getEndPage()).boxed().toList()
                , "pageDto", pageDto);
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
