package com.example.todo.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ResponseTodoDto {
    String message;
    List<TodoDto> data;
    public ResponseTodoDto(String message, TodoDto dto) {
        this.message = message;
        if (dto != null) {
            this.data = new ArrayList<>(List.of(dto));
        }
    }
    public ResponseTodoDto(String message, List<TodoDto> data) {
        this.message = message;
        if (data != null) {
            this.data = data;
        }
    }
    public ResponseTodoDto(String message) {
        this.message = message;
    }
}
