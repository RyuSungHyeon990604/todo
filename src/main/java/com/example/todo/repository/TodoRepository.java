package com.example.todo.repository;

import com.example.todo.dto.TodoUpdateRequestDto;
import com.example.todo.entity.Todo;

import java.util.List;

public interface TodoRepository {

    List<Todo> findAll(Long userId, Long page);

    Todo findById(Long id);

    Todo insert(Todo todo);

    int deleteById(Long id);

    int update(Long id, TodoUpdateRequestDto todoDto);

}
