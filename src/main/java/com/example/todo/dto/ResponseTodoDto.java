package com.example.todo.dto;


import com.example.todo.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ResponseTodoDto {
    private Long id;
    private Long userId;
    private String userName;
    private String todo;
    private LocalDateTime modDt;
    public ResponseTodoDto(Todo todo) {
        this.id = todo.getId();
        this.userName = todo.getUser().getName();
        this.userId = todo.getUser().getId();
        this.todo = todo.getTodo();
        this.modDt = todo.getModDt();
    }
}
