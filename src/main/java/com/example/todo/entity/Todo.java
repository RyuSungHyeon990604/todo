package com.example.todo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class Todo {
    private long id;
    private long userId;
    private String todo;
    private Date createDt;
    private Date modDt;
}
