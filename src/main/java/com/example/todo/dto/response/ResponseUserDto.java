package com.example.todo.dto.response;

import com.example.todo.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseUserDto {
    private Long id;
    private Long userId;
    private String name;
    private String email;
    private LocalDateTime modDt;
    public ResponseUserDto(User user) {
        this.id = user.getId();
        this.userId = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.modDt = user.getModDt();
    }
}
