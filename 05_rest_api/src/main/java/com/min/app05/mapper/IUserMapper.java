package com.min.app05.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.min.app05.model.dto.InsertUserDto;
import com.min.app05.model.dto.UpdateUserDto;
import com.min.app05.model.dto.UserDto;

@Mapper
public interface IUserMapper {
  int insertUser(InsertUserDto insertUserDto);
  int updateUser(UpdateUserDto updateUserDto);
  int deleteUser(int userId);
  int selectUserCount();
  List<UserDto> selectUserList(Map<String, Object> map);
  UserDto selectUserById(int userId);
}
