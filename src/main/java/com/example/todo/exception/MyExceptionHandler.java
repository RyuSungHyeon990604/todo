package com.example.todo.exception;

import com.example.todo.code.ErrorCode;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(MyException.class)
    public ResponseEntity<ExceptionResponse> accessDeniedException(MyException e) {
        String errorMessage = e.getErrorMessage();
        String errorCode = e.getErrorCode();
        ExceptionResponse response = new ExceptionResponse(errorMessage, errorCode);
        return ResponseEntity.badRequest().body(response);
    }

    //request 유효성 체크
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorCode errorCode = ErrorCode.METHOD_ARGUMENT_NOT_VALID;
        // 유효성 검사 실패한 모든 필드와 에러 메시지를 추출
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<String> errorMessages = fieldErrors.stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        ExceptionResponse response = new ExceptionResponse("Validation failed" + errorMessages, errorCode.getCode());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ExceptionResponse> duplicateKeyException(DuplicateKeyException e) {
        String errorField = getDuplicateField(e.getMessage());
        String code = ErrorCode.DUPLICATE_KEY.getCode();
        String errorMessage = ErrorCode.DUPLICATE_KEY.getMessage(errorField);
        ExceptionResponse response = new ExceptionResponse(errorMessage, code);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ExceptionResponse> missingRequestHeaderException(MissingRequestHeaderException e) {
        String missingHeader = e.getHeaderName();
        String code = ErrorCode.MISSING_REQUEST_HEADER.getCode();
        String errorMessage = ErrorCode.MISSING_REQUEST_HEADER.getMessage(missingHeader);
        ExceptionResponse response = new ExceptionResponse(errorMessage, code);
        return ResponseEntity.badRequest().body(response);
    }

    private String getDuplicateField(String message) {
        Pattern pattern = Pattern.compile("for key '(.+?)'"); // MySQL 기준 패턴
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            return matcher.group(1); // ✅ 중복된 필드명 반환
        }
        return "unknown";
    }
}
