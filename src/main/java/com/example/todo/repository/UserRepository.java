package com.example.todo.repository;

import com.example.todo.dto.UserDto;
import com.example.todo.entity.User;

import java.util.Optional;

public interface UserRepository {
    User insert(UserDto user);

    int update(Long id, String name, String email);

    User findById(Long id);
}
