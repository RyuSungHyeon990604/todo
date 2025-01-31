package com.example.todo.exception;

import com.example.todo.code.ErrorCode;
import lombok.Getter;


@Getter
public class ExceptionResponse {
    String message;
    int code;
    public ExceptionResponse(String message, ErrorCode code) {
        this.message = message;
        this.code = code.getCode();
    }
}
