package com.example.todo.exception;

import com.example.todo.code.ErrorCode;

public class FailToCreateTodoException extends MyException {
    public FailToCreateTodoException(ErrorCode errorCode) {
        super(errorCode);
    }
}
