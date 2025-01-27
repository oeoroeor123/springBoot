package com.min.app05.service;

import java.util.List;

import com.min.app05.model.dto.InsertUserDto;
import com.min.app05.model.dto.UpdateUserDto;
import com.min.app05.model.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;

public interface IUserService {
  InsertUserDto registUser(InsertUserDto insertUserDto);
  UpdateUserDto modifyUser(UpdateUserDto updateUserDto) throws Exception ;
  void removeUser(int userId) throws Exception;
  List<UserDto> getUsers(HttpServletRequest request);
  UserDto getUserById(int userId) throws Exception;
}
