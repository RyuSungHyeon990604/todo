package com.example.todo.dto;

import com.example.todo.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@AllArgsConstructor
public class TodoDto {
    private Long id;
    private Long userId;
    private String todo;
    private String pwd;
    private LocalDateTime modDt;
    public TodoDto(Todo todo) {
        this.id = todo.getId();
        this.userId = todo.getUserId();
        this.todo = todo.getTodo();
        this.pwd = todo.getPwd();
        this.modDt = todo.getModDt();
    }
}
