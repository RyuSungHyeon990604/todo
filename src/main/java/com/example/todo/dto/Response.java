package com.example.todo.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Response<T> {
    private final List<T> data;
    private final String message;
    public Response(List<T> data, String message) {
        this.data = data;
        this.message = message;
    }
    public Response(T data, String message) {
        this.data = new ArrayList<>(List.of(data));
        this.message = message;
    }
    public Response(String message) {
        this.data = null;
        this.message = message;
    }
}
