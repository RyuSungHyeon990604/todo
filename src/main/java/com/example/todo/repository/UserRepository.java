package com.example.todo.repository;

import com.example.todo.dto.request.UserUpdateRequestDto;
import com.example.todo.entity.User;

public interface UserRepository {
    User insert(User user);

    int update(Long id, UserUpdateRequestDto user);

    User findById(Long id);

    boolean isDuplicate(String columnName, String value);
}
