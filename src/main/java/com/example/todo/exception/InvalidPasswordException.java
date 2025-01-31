package com.example.todo.exception;

import com.example.todo.code.ErrorCode;

public class InvalidPasswordException extends MyException {
    public InvalidPasswordException() {
        super(ErrorCode.INVALID_PASSWORD);
    }
}
