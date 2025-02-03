package com.example.todo.controller;

import com.example.todo.dto.*;
import com.example.todo.dto.request.TodoCreateRequestDto;
import com.example.todo.dto.request.TodoDeleteRequestDto;
import com.example.todo.dto.request.TodoFindRequestDto;
import com.example.todo.dto.request.TodoUpdateRequestDto;
import com.example.todo.dto.response.ResponseTodoDto;
import com.example.todo.service.TodoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("")
    public ResponseEntity<ResponseDto<ResponseTodoDto>> findAll(@ModelAttribute @Valid TodoFindRequestDto requestDto) {
        List<ResponseTodoDto> all = todoService.findAll(requestDto.getUserId(), requestDto.getPage(), requestDto.getPageSize(), requestDto.getLocalDate());
        return ResponseEntity.ok(new ResponseDto<>(all,"success"));
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<ResponseDto<ResponseTodoDto>> findById(@PathVariable Long todoId){
        ResponseTodoDto byId = todoService.findById(todoId);
        return ResponseEntity.ok(new ResponseDto<>(byId, "success"));
    }

    @PostMapping("")
    public ResponseEntity<ResponseDto<ResponseTodoDto>> create(@RequestHeader("userId") Long userId, @RequestBody @Valid TodoCreateRequestDto createDto){
        ResponseTodoDto insert = todoService.insert(userId,createDto);
        return ResponseEntity.ok(new ResponseDto<>(insert, "created"));
    }

    @PatchMapping("/{todoId}")
    public ResponseEntity<ResponseDto<Void>> update(@RequestHeader("userId") Long userId, @PathVariable Long todoId, @RequestBody @Valid TodoUpdateRequestDto updateDto){
        int updated = todoService.update(userId, todoId, updateDto);
        return ResponseEntity.ok(new ResponseDto<>("success"));
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<ResponseDto<Void>> delete(@RequestHeader("userId") Long userId, @PathVariable Long todoId, @RequestBody @Valid TodoDeleteRequestDto deleteDto){
        int deleted = todoService.deleteById(userId, todoId, deleteDto);
        return ResponseEntity.ok(new ResponseDto<>("success"));
    }

}
