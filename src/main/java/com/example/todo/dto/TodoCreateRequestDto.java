package com.example.todo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class TodoCreateRequestDto {
    @NotNull
    private Long userId;
    @NotNull
    @Size(max = 200)
    private String todo;
    @NotNull
    private String pwd;
}