package com.example.todo.exception;

import com.example.todo.code.ErrorCode;
import lombok.Getter;


@Getter
public class ExceptionResponse {
    String message;
    String code;
    public ExceptionResponse(String message, String code) {
        this.message = message;
        this.code = code;
    }
}
