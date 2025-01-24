package com.min.app05.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.min.app05.mapper.IUserMapper;
import com.min.app05.model.dto.UserDto;
import com.min.app05.model.exception.UserNotFoundException;
import com.min.app05.service.IUserService;
import com.min.app05.util.PageUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

  private final IUserMapper userMapper;
  private final PageUtil pageUtil;
  
  @Override
  public UserDto registUser(UserDto userDto) throws Exception {
    userDto.setCreateDt(new Timestamp(System.currentTimeMillis()));
    userMapper.insertUser(userDto);
    return userDto;
  }
  
  @Override
  public UserDto modifyUser(UserDto userDto) throws Exception {
    userMapper.updateUser(userDto);
    return userDto;
  }
  
  @Override
  public int removeUser(int userId) throws Exception {
    return userMapper.deleteUser(userId);
  }

  @Override
  public List<UserDto> getUsers(HttpServletRequest request) throws Exception {
    Optional<String> optPage = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(optPage.orElse("1"));
    Optional<String> optDisplay = Optional.ofNullable(request.getParameter("display"));
    int display = Integer.parseInt(optDisplay.orElse("20"));
    int count = userMapper.selectUserCount();
    pageUtil.setPaging(page, display, count);
    Optional<String> optSort = Optional.ofNullable(request.getParameter("sort"));
    String sort = optSort.orElse("DESC");
    return userMapper.selectUserList(Map.of("sort", sort
                                          , "offset", pageUtil.getOffset()
                                          , "display", display));
  }
  
  @Override
  public UserDto getUserById(int userId) throws Exception {
    UserDto foundUser = userMapper.selectUserById(userId);
    if(foundUser == null)
      throw new UserNotFoundException("해당 회원 번호를 가진 사용자 정보를 찾을 수 없습니다.");
    return foundUser;
  }
}
