package com.example.todo.service;

import com.example.todo.dto.request.UserCreateRequestDto;
import com.example.todo.dto.request.UserUpdateRequestDto;
import com.example.todo.dto.response.ResponseUserDto;

public interface UserService {
    ResponseUserDto addUser(UserCreateRequestDto userDto);
    int updateUser(Long id, UserUpdateRequestDto userDto);
}
