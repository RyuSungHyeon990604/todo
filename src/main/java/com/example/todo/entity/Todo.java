package com.example.todo.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class Todo {
    private Long id;
    private User user;
    private String todo;
    private String pwd;
    private LocalDateTime createDt;
    private LocalDateTime modDt;

    @Builder
    public Todo(Long id, User user, String todo, String pwd, LocalDateTime createDt, LocalDateTime modDt) {
        this.id = id;
        this.user = user;
        this.todo = todo;
        this.pwd = pwd;
        this.createDt = createDt;
        this.modDt = modDt;
    }
}
