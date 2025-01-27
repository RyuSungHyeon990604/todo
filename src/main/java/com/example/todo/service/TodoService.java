package com.example.todo.service;

import com.example.todo.dto.response.ResponseTodoDto;
import com.example.todo.dto.request.TodoCreateRequestDto;
import com.example.todo.dto.request.TodoUpdateRequestDto;

import java.util.List;

public interface TodoService {
    List<ResponseTodoDto> findAll(Long userId,Long page);
    ResponseTodoDto findById(Long id);
    int deleteById(Long id);
    ResponseTodoDto insert(TodoCreateRequestDto todoDto);
    int update(Long id, TodoUpdateRequestDto todoDto);

}
