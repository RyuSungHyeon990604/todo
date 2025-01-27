package com.example.todo.service;

import com.example.todo.dto.response.UserDto;

public interface UserService {
    UserDto addUser(UserDto userDto);
    int updateUser(Long id, UserDto userDto);
}
