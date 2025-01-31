package com.example.todo.exception;

import com.example.todo.code.ErrorCode;

public class TodoNotFoundException extends MyException {

    public TodoNotFoundException() {
        super(ErrorCode.TODO_NOT_FOUND);
    }
}
