package com.example.todo.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ResponseDto<T> {
    private final List<T> data;
    private final String message;
    public ResponseDto(List<T> data, String message) {
        this.data = data;
        this.message = message;
    }
    public ResponseDto(T data, String message) {
        this.data = new ArrayList<>(List.of(data));
        this.message = message;
    }
    public ResponseDto(String message) {
        this.data = null;
        this.message = message;
    }
}
