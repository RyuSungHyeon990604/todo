package com.example.todo.entity;

import com.example.todo.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createDt;
    private LocalDateTime  modDt;
    public User(String name, String email, LocalDateTime  createDt, LocalDateTime  modDt) {
        this.name = name;
        this.email = email;
        this.createDt = createDt;
        this.modDt = modDt;
    }
    public User(UserDto dto){
        this.name = dto.getName();
        this.email = dto.getEmail();
    }
}
