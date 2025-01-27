package com.example.todo.controller;

import com.example.todo.dto.*;
import com.example.todo.dto.request.TodoCreateRequestDto;
import com.example.todo.dto.request.TodoDeleteRequestDto;
import com.example.todo.dto.request.TodoUpdateRequestDto;
import com.example.todo.dto.response.ResponseTodoDto;
import com.example.todo.service.TodoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ResponseDto<ResponseTodoDto>> findAll(@RequestParam(value = "userId", required = false) Long userId, @RequestParam(value = "page", defaultValue = "0") Long page){
        List<ResponseTodoDto> all = todoService.findAll(userId,page);
        return ResponseEntity.ok(new ResponseDto<>(all,"success"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<ResponseTodoDto>> findById(@PathVariable Long id){
        ResponseTodoDto byId = todoService.findById(id);
        return ResponseEntity.ok(new ResponseDto<>(byId, "success"));
    }

    @PostMapping("")
    public ResponseEntity<ResponseDto<ResponseTodoDto>> create(@RequestBody @Valid TodoCreateRequestDto createDto){
        ResponseTodoDto insert = todoService.insert(createDto);
        return ResponseEntity.ok(new ResponseDto<>(insert, "created"));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> update(@PathVariable Long id, @RequestBody @Valid TodoUpdateRequestDto updateDto){
        int updated = todoService.update(id, updateDto);
        return ResponseEntity.ok(new ResponseDto<>("success"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> delete(@PathVariable Long id, @RequestBody @Valid TodoDeleteRequestDto deleteDto){
        int deleted = todoService.deleteById(id,deleteDto);
        return ResponseEntity.ok(new ResponseDto<>("success"));
    }

}
