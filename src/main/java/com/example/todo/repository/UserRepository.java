package com.example.todo.repository;

import com.example.todo.dto.request.UserUpdateRequestDto;
import com.example.todo.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> insert(User user);

    int update(Long id, UserUpdateRequestDto user);

    Optional<User> findById(Long id);
}
