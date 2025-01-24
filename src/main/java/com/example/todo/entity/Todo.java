package com.example.todo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@AllArgsConstructor
public class Todo {
    private Long id;
    private Long userId;
    private String todo;
    private String pwd;
    private LocalDateTime createDt;
    private LocalDateTime modDt;
}
