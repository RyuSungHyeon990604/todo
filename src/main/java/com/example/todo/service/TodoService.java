package com.example.todo.service;

import com.example.todo.dto.TodoDto;

import java.util.List;

public interface TodoService {
    List<TodoDto> findAll();
    TodoDto findById(Long id);
    void deleteById(Long id);
    TodoDto insert(Long userId, TodoDto todoDto);
    void update(Long id, TodoDto todoDto);

}
