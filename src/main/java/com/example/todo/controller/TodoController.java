package com.example.todo.controller;

import com.example.todo.dto.*;
import com.example.todo.dto.request.TodoCreateRequestDto;
import com.example.todo.dto.request.TodoDeleteRequestDto;
import com.example.todo.dto.request.TodoUpdateRequestDto;
import com.example.todo.dto.response.ResponseTodoDto;
import com.example.todo.service.TodoService;
import com.example.todo.validation.DateCheck;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@Validated
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("")
    public ResponseEntity<Response<ResponseTodoDto>> findAll(@RequestParam(required = false) @PositiveOrZero Long userId,
                                                             @RequestParam(required = false) @DateCheck String date,
                                                             @RequestParam(defaultValue = "1") @Positive Long page,
                                                             @RequestParam(defaultValue = "5") @Positive Long pageSize) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate inputDate = null;
        if(date != null) {
            inputDate = LocalDate.parse(date, formatter);
        }
        List<ResponseTodoDto> all = todoService.findAll(userId, page, pageSize, inputDate);
        return ResponseEntity.ok(new Response<>(all,"success"));
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<Response<ResponseTodoDto>> findById(@PathVariable Long todoId){
        ResponseTodoDto byId = todoService.findById(todoId);
        return ResponseEntity.ok(new Response<>(byId, "success"));
    }

    @PostMapping("")
    public ResponseEntity<Response<ResponseTodoDto>> create(@RequestHeader("userId") Long userId, @RequestBody @Valid TodoCreateRequestDto createDto){
        ResponseTodoDto insert = todoService.insert(userId,createDto);
        return ResponseEntity.ok(new Response<>(insert, "created"));
    }

    @PatchMapping("/{todoId}")
    public ResponseEntity<Response<Void>> update(@RequestHeader("userId") Long userId, @PathVariable Long todoId, @RequestBody @Valid TodoUpdateRequestDto updateDto){
        int updated = todoService.update(userId, todoId, updateDto);
        return ResponseEntity.ok(new Response<>("success"));
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<Response<Void>> delete(@RequestHeader("userId") Long userId, @PathVariable Long todoId, @RequestBody @Valid TodoDeleteRequestDto deleteDto){
        int deleted = todoService.deleteById(userId, todoId, deleteDto);
        return ResponseEntity.ok(new Response<>("success"));
    }

}
