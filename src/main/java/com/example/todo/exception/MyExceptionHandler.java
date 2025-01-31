package com.example.todo.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class MyExceptionHandler {

    //request 유효성 체크
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

    //일정등록 실패했을때
    @ExceptionHandler(FailToCreateTodoException.class)
    public ResponseEntity<ExceptionResponse> failToCreateTodoException(FailToCreateTodoException e){
        ExceptionResponse response = new ExceptionResponse(e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    //사용자 데이터가 존재하지않을때
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> userNotFoundException(UserNotFoundException e){
        ExceptionResponse response = new ExceptionResponse(e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    //일정 데이터가 존재하지않을때
    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<ExceptionResponse> todoNotFoundException(TodoNotFoundException e){
        ExceptionResponse response = new ExceptionResponse(e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    //비밀번호 오류
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ExceptionResponse> invalidPasswordException(InvalidPasswordException e){
        ExceptionResponse response = new ExceptionResponse(e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    //권한이없는 데이터를 수정, 삭제 접근했을때
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> accessDeniedException(AccessDeniedException e){
        ExceptionResponse response = new ExceptionResponse(e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    //중복된 키값(unique, pk)
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ExceptionResponse> duplicateKeyException(DuplicateKeyException e){
        ExceptionResponse response = new ExceptionResponse(e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ExceptionResponse> missingRequestHeaderException(MissingRequestHeaderException e){
        ExceptionResponse response = new ExceptionResponse(e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
}
