package com.min.app05.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.min.app05.mapper.IUserMapper;
import com.min.app05.model.dto.InsertUserDto;
import com.min.app05.model.dto.UpdateUserDto;
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
  public InsertUserDto registUser(InsertUserDto insertUserDto) {
    userMapper.insertUser(insertUserDto);
    return insertUserDto;
  }
  
  @Override
  public UpdateUserDto modifyUser(UpdateUserDto updateUserDto) throws Exception {
    int updatedCount = userMapper.updateUser(updateUserDto);
    // 수정된 건수가 0 이면 회원을 찾지 못하고 예외 처리한다.
    if(updatedCount == 0)
      throw new UserNotFoundException("회원 조회 실패로 인한 회원 정보 수정 오류");
    return updateUserDto;
  }
  
  @Override
  public void removeUser(int userId) throws Exception {
    int deletedCount = userMapper.deleteUser(userId);
    if(deletedCount == 0)
      throw new UserNotFoundException("회원 조회 실패로 인한 회원 정보 삭제 오류");
  }

  @Override
  public List<UserDto> getUsers(HttpServletRequest request) {
    int page = Integer.parseInt(request.getParameter("page"));
    int display = Integer.parseInt(request.getParameter("display"));
    int count = userMapper.selectUserCount();
    pageUtil.setPaging(page, display, count);
    String sort = request.getParameter("sort");
    return userMapper.selectUserList(Map.of("sort", sort
                                          , "offset", pageUtil.getOffset()
                                          , "display", display));
  }
    
  @Override
  public UserDto getUserById(int userId) throws Exception {
    UserDto foundUser = userMapper.selectUserById(userId);
    if(foundUser == null)
      throw new UserNotFoundException("회원 조회 실패");
    return foundUser;
  }
}
