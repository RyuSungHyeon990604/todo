package com.example.todo.entity;

import com.example.todo.dto.TodoDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@AllArgsConstructor
public class Todo {
    private Long id;
    private User user;
    private String todo;
    private String pwd;
    private LocalDateTime createDt;
    private LocalDateTime modDt;

    public Todo(User user, TodoDto todoDto) {
        this.user = user;
        this.todo = todoDto.getTodo();
        this.pwd = todoDto.getPwd();
    }
}
