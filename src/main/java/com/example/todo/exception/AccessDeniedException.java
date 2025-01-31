package com.example.todo.exception;

import com.example.todo.code.ErrorCode;

public class AccessDeniedException extends MyException {
    public AccessDeniedException() {
        super(ErrorCode.UNAUTHORIZED);
    }
}
