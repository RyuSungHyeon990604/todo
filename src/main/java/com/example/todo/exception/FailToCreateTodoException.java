package com.example.todo.exception;

public class FailToCreateTodoException extends RuntimeException {
    public FailToCreateTodoException() {
        super();
    }
    public FailToCreateTodoException(String message) {
        super(message);
    }
    public FailToCreateTodoException(String message, Throwable cause) {
        super(message, cause);
    }
}
