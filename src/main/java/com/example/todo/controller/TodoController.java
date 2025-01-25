package com.example.todo.controller;

import com.example.todo.dto.ResponseTodoDto;
import com.example.todo.dto.TodoDto;
import com.example.todo.exception.DbException;
import com.example.todo.service.TodoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionOverrideException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
    public ResponseEntity<ResponseTodoDto> findAll(HttpServletRequest request){
        String userId = request.getParameter("userId");
        String page = request.getParameter("page");

        Long userIdValue = null;
        Long pageValue = null;
        if(userId != null && !userId.equals("null")){
            userIdValue = Long.parseLong(userId);
        }
        if(page != null && !page.equals("null")){
            pageValue = Long.parseLong(page);
        }
        try {
            List<TodoDto> all = todoService.findAll(userIdValue,pageValue);
            return ResponseEntity.status(200).body(new ResponseTodoDto("success", all));
        } catch (DbException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseTodoDto(e.getMessage()));
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseTodoDto> findById(@PathVariable Long id){
        try {
            TodoDto byId = todoService.findById(id);
            return ResponseEntity.status(200).body(new ResponseTodoDto("success", byId));
        } catch (DbException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseTodoDto(e.getMessage()));
        }

    }

    @PostMapping("")
    public ResponseEntity<ResponseTodoDto> create(@RequestBody TodoDto todoDto){
        try{
            TodoDto insert = todoService.insert(todoDto);
            return ResponseEntity.status(201).body(new ResponseTodoDto("created", insert));
        } catch(DbException e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseTodoDto(e.getMessage()));
        }

    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseTodoDto> update(@PathVariable Long id, @RequestBody TodoDto todoDto){
        try {
            int updated = todoService.update(id, todoDto);
            if(updated == 0){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseTodoDto("0 rows updated"));
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseTodoDto("success", todoDto));
        } catch (DbException e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseTodoDto(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseTodoDto> delete(@PathVariable Long todoId){
        try {
            int deleted = todoService.deleteById(todoId);
            if(deleted == 0){
                return ResponseEntity.status(404).build();
            }
            return ResponseEntity.status(200).body(new ResponseTodoDto("success"));
        } catch (DbException e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseTodoDto(e.getMessage()));
        }
    }

}
