package com.example.todo.code;

public enum ErrorCode {
    INVALID_INPUT_VALUE(400),
    DUPLICATE_KEK(500),
    USER_NOT_FOUND(400),
    TODO_NOT_FOUND(400),
    INVALID_PASSWORD(400),
    UNAUTHORIZED(400),
    DATABASE_EXCEPTION(500),
    MISSING_REQUEST_HEADER(400);
    final int code;
    ErrorCode(int code) {
        this.code = code;
    }
    public int getCode(){
        return code;
    }
}
