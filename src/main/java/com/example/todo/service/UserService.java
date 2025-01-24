package com.example.todo.service;

import com.example.todo.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto addUser(UserDto userDto);
    int updateUser(Long id, UserDto userDto);
}
