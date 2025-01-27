package com.example.todo.dto.request;

import com.example.todo.entity.Todo;
import com.example.todo.entity.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TodoCreateRequestDto {
    @NotNull
    private Long userId;
    @NotNull
    @Size(max = 200)
    private String todo;
    @NotNull
    private String pwd;

    public Todo toEntity(User user){
        return Todo.builder()
                .user(user)
                .todo(todo)
                .pwd(pwd)
                .build();
    }
}