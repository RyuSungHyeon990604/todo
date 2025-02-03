package com.example.todo.exception;

import com.example.todo.code.ErrorCode;

public class FailToCreateException extends MyException {
    public FailToCreateException(ErrorCode errorCode) {
        super(errorCode);
    }
}
