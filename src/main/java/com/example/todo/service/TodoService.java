package com.example.todo.service;

import com.example.todo.dto.request.TodoDeleteRequestDto;
import com.example.todo.dto.response.ResponseTodoDto;
import com.example.todo.dto.request.TodoCreateRequestDto;
import com.example.todo.dto.request.TodoUpdateRequestDto;

import java.util.List;

public interface TodoService {
    List<ResponseTodoDto> findAll(Long userId,Long page);
    ResponseTodoDto findById(Long todoId);
    int deleteById(Long userId, Long todoId, TodoDeleteRequestDto requestDto);
    ResponseTodoDto insert(Long userId, TodoCreateRequestDto requestDto);
    int update(Long userId, Long todoId, TodoUpdateRequestDto requestDto);

}
