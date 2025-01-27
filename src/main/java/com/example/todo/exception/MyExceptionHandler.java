package com.example.todo.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class MyExceptionHandler {

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

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<ExceptionResponse> todoNotFoundException(TodoNotFoundException e){
        ExceptionResponse response = new ExceptionResponse(e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ExceptionResponse> dbException(DataAccessException e){
        ExceptionResponse response = new ExceptionResponse(e.getMessage());
        return ResponseEntity.internalServerError().body(response);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ExceptionResponse> invalidPasswordException(InvalidPasswordException e){
        ExceptionResponse response = new ExceptionResponse(e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> accessDeniedException(AccessDeniedException e){
        ExceptionResponse response = new ExceptionResponse(e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
}
