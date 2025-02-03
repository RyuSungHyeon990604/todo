package com.example.todo.dto.request;

import com.example.todo.validation.DateCheck;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
public class TodoFindRequestDto {

    @PositiveOrZero
    private Long userId;

    @DateCheck
    private String date;

    @Positive
    private Long page = 1L;

    @Positive
    private Long pageSize = 1L;

    public TodoFindRequestDto(Long userId, String date, Long page, Long pageSize) {
        this.userId = userId;
        this.date = date;
        if (page != null) this.page = page;
        if (pageSize != null) this.pageSize = pageSize;
    }

    public LocalDate getLocalDate() {
        if(date == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }

}
