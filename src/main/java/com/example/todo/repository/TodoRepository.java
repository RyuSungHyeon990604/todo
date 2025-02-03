package com.example.todo.repository;

import com.example.todo.dto.request.TodoUpdateRequestDto;
import com.example.todo.entity.Todo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TodoRepository {

    List<Todo> findAll(Long userId, Long page, Long pageSize, LocalDate date);

    Optional<Todo> findById(Long id);

    Todo insert(Todo todo);

    int deleteById(Long id);

    int update(Long todoId, TodoUpdateRequestDto requestDto);

}
