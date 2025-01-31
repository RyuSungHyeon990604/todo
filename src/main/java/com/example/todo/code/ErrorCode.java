package com.example.todo.code;

public enum ErrorCode {
    METHOD_ARGUMENT_NOT_VALID("0002","입력값이 올바르지 않습니다"),
    MISSING_REQUEST_HEADER("0003","필수요청 헤더가 누락되었습니다 : %s"),
    DB_ERROR("0004",null),
    DUPLICATE_KEY("0005","이미 존재하는 데이터입니다 : %s"),

    TODO_NOT_FOUND("1000","일정이 없습니다"),
    INVALID_PASSWORD("1001","비밀번호가 올바르지않습니다"),

    USER_NOT_FOUND("2000","사용자를 찾을수없습니다"),
    UNAUTHORIZED("2001","권한이 없습니다");


    final String code;
    final String message;
    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
    public String getCode(){
        return code;
    }
    public String getMessage(Object... args){
        return String.format(message,  args);
    }
}
