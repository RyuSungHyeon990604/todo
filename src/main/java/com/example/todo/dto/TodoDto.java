package com.example.todo.dto;

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
}
