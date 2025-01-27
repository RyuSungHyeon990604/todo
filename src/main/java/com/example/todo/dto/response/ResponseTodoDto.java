package com.example.todo.dto.response;


import com.example.todo.entity.Todo;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseTodoDto {
    private Long id;
    private Long userId;
    private String userName;
    private String todo;
    private String pwd;
    private LocalDateTime modDt;
    public ResponseTodoDto(Todo todo) {
        this.id = todo.getId();
        this.userName = todo.getUser().getName();
        this.userId = todo.getUser().getId();
        this.todo = todo.getTodo();
        this.pwd = todo.getPwd();
        this.modDt = todo.getModDt();
    }
}
