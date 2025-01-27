package com.example.todo.exception;

public class DbException extends RuntimeException {
    public DbException() {
        super();
    }
    public DbException(String message) {
        super(message);
    }
    public DbException(String message, Throwable cause) {
        super(message,cause);
    }
}
