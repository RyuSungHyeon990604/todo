package com.example.todo.repository;

import com.example.todo.dto.TodoDto;
import com.example.todo.entity.Todo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository {

    List<Todo> findAll();

    Todo findById(Long id);

    List<Todo> findAllByUserId(Long userId);

    Todo insert(Todo todo);

    int deleteById(Long id);

    int update(Long id, TodoDto todoDto);

}
