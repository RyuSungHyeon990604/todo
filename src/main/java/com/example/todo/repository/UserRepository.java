package com.example.todo.repository;

import com.example.todo.dto.UserDto;
import com.example.todo.entity.User;

import java.util.Optional;

public interface UserRepository {
    User insert(User user);

    int update(Long id, UserDto user);

    User findById(Long id);
}
