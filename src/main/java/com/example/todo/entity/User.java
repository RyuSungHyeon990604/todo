package com.example.todo.entity;

import com.example.todo.dto.response.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createDt;
    private LocalDateTime  modDt;
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
    public User(UserDto dto){
        this.name = dto.getName();
        this.email = dto.getEmail();
    }
}
