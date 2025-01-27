package com.example.todo.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserUpdateRequestDto {
    @NotNull
    String name;
    @Email
    @NotNull
    String email;
}
