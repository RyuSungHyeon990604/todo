package com.example.todo.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ValidExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> methodArgumentNotValidException(MethodArgumentNotValidException e){
        // 유효성 검사 실패한 모든 필드와 에러 메시지를 추출
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<String> errorMessages = fieldErrors.stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        ExceptionResponse response = new ExceptionResponse("Validation failed" + errorMessages);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(FailToCreateTodoException.class)
    public ResponseEntity<ExceptionResponse> failToCreateTodoException(FailToCreateTodoException e){
        ExceptionResponse response = new ExceptionResponse(e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(DbException.class)
    public ResponseEntity<ExceptionResponse> dbException(DbException e){
        ExceptionResponse response = new ExceptionResponse(e.getMessage());
        return ResponseEntity.internalServerError().body(response);
    }
}
