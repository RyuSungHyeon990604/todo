package com.example.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class UserDto {
    private long id;
    private String name;
    private String email;
    private Date mod_dt;
}
