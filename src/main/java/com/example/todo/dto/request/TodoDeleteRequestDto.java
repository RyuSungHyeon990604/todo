package com.example.todo.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class TodoDeleteRequestDto {
    @NotNull
    private String pwd;

    @NotNull
    private Long userId;
}
