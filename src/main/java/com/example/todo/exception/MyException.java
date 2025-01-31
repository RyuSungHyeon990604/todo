package com.example.todo.exception;

import com.example.todo.code.ErrorCode;

public class MyException extends RuntimeException {
    private final ErrorCode error;
    public MyException(ErrorCode error) {
        super(error.getMessage());
        this.error = error;
    }

    protected String getErrorMessage() {
        return error.getMessage();
    }
    public String getErrorCode() {
        return error.getCode();
    }
}
