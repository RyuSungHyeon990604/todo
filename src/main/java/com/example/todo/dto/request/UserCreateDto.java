package com.example.todo.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserCreateDto {
    @NotNull
    private String name;
    @Email
    @NotNull
    private String email;
}
