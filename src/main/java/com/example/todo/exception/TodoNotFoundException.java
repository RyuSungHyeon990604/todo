package com.example.todo.exception;

public class TodoNotFoundException extends RuntimeException {
    public TodoNotFoundException() {
        super();
    }
    public TodoNotFoundException(String message) {
        super(message);
    }
    public TodoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
