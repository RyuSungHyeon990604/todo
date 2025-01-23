package com.example.todo.dto;

import com.example.todo.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Date;

@Getter
@AllArgsConstructor
public class TodoDto {
    private long id;
    private long userId;
    private String todo;
    private String pwd;
    private Date modDt;
    public TodoDto(Todo todo) {
        this.id = todo.getId();
        this.userId = todo.getUserId();
        this.todo = todo.getTodo();
        this.pwd = todo.getPwd();
        this.modDt = todo.getModDt();
    }
}
