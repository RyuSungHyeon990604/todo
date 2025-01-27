package com.example.todo.repository;

import com.example.todo.dto.response.UserDto;
import com.example.todo.entity.User;

public interface UserRepository {
    User insert(User user);

    int update(Long id, UserDto user);

    User findById(Long id);
}
