package com.example.todo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class User {
    private long id;
    private String name;
    private String email;
    private Date create_dt;
    private Date mod_dt;
    public User(String name, String email, Date create_dt, Date mod_dt) {
        this.name = name;
        this.email = email;
        this.create_dt = create_dt;
        this.mod_dt = mod_dt;
    }
}
