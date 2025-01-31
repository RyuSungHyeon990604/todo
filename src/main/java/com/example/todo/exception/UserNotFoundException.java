package com.example.todo.exception;

import com.example.todo.code.ErrorCode;

public class UserNotFoundException extends MyException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
