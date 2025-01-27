package com.example.todo.controller;

import com.example.todo.dto.*;
import com.example.todo.dto.request.TodoCreateRequestDto;
import com.example.todo.dto.request.TodoUpdateRequestDto;
import com.example.todo.dto.response.ResponseTodoDto;
import com.example.todo.service.TodoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ResponseDto<ResponseTodoDto>> findAll(HttpServletRequest request){
        String userId = request.getParameter("userId");
        String page = request.getParameter("page");

        Long userIdValue = null;
        Long pageValue = 1L;
        if(userId != null && !userId.equals("null")){
            userIdValue = Long.parseLong(userId);
        }
        if(page != null && !page.equals("null")){
            pageValue = Long.parseLong(page);
        }
        List<ResponseTodoDto> all = todoService.findAll(userIdValue,pageValue);
        return ResponseEntity.status(200).body(new ResponseDto<ResponseTodoDto>(all,"success"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<ResponseTodoDto>> findById(@PathVariable Long id){
        ResponseTodoDto byId = todoService.findById(id);
        return ResponseEntity.status(200).body(new ResponseDto<>(byId, "success"));
    }

    @PostMapping("")
    public ResponseEntity<ResponseDto<ResponseTodoDto>> create(@RequestBody @Valid TodoCreateRequestDto createDto){
        ResponseTodoDto insert = todoService.insert(createDto);
        return ResponseEntity.status(201).body(new ResponseDto<>(insert, "created"));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto<ResponseTodoDto>> update(@PathVariable Long id, @RequestBody @Valid TodoUpdateRequestDto updateDto){
        int updated = todoService.update(id, updateDto);
//        if(updated == 0){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto<>("0 rows updated"));
//        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>("success"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<ResponseTodoDto>> delete(@PathVariable Long id){
        int deleted = todoService.deleteById(id);
//        if(deleted == 0){
//            return ResponseEntity.status(404).build();
//        }
        return ResponseEntity.status(200).body(new ResponseDto<>("success"));
    }

}
