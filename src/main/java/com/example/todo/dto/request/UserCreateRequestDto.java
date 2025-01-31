package com.example.todo.dto.request;

import com.example.todo.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserCreateRequestDto {
    @NotNull
    private String name;
    @Email
    @NotNull
    private String email;

    public User toEntity() {
        return new User(name, email);
    }
}
