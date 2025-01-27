package com.example.todo.dto;

import com.example.todo.entity.Todo;
import lombok.*;
import java.time.LocalDateTime;

@Getter
public class TodoDto {
    private Long id;
    private Long userId;
    private String userName;
    private String todo;
    private String pwd;
    private LocalDateTime modDt;
    public TodoDto(Todo todo) {
        this.id = todo.getId();
        this.userName = todo.getUser().getName();
        this.userId = todo.getUser().getId();
        this.todo = todo.getTodo();
        this.modDt = todo.getModDt();
    }

    public TodoDto(TodoUpdateRequestDto updateRequestDto) {
        this.todo = updateRequestDto.getTodo();
    }

    public TodoDto(TodoCreateRequestDto createRequestDto){
        this.todo = createRequestDto.getTodo();
        this.userId = createRequestDto.getUserId();
        this.pwd = createRequestDto.getPwd();
    }
}
