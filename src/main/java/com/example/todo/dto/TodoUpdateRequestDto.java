package com.example.todo.dto;

import com.example.todo.entity.Todo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TodoUpdateRequestDto {
    @NotNull
    @Size(max = 200)
    String todo;

    public Todo toEntity(){
        return Todo.builder().todo(todo).build();
    }
}
