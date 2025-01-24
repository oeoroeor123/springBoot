package com.min.app05.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.min.app05.model.dto.UserDto;

@Mapper
public interface IUserMapper {
  int insertUser(UserDto userDto) throws Exception;
  int updateUser(UserDto userDto) throws Exception;
  int deleteUser(int userId) throws Exception;
  int selectUserCount() throws Exception;
  List<UserDto> selectUserList(Map<String, Object> map) throws Exception;
  UserDto selectUserById(int userId) throws Exception;
}
