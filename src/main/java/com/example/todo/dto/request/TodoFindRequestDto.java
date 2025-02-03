package com.example.todo.dto.request;

import com.example.todo.validation.DateCheck;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
public class TodoFindRequestDto {

    @PositiveOrZero
    private Long userId;

    @DateCheck
    private String date;

    private Long page;

    public LocalDate getLocalDate() {
        if(date == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }

}
