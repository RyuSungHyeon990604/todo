package com.example.todo.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoDeleteRequestDto {
    @NotNull
    private String pwd;
}
