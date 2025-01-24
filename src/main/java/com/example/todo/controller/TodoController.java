package com.example.todo.controller;

import com.example.todo.dto.TodoDto;
import com.example.todo.service.TodoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<List<TodoDto>> findAll(HttpServletRequest request){
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
        List<TodoDto> all = todoService.findAll(userIdValue,pageValue);
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> findById(@PathVariable Long id){
        TodoDto byId = todoService.findById(id);
        return ResponseEntity.ok(byId);
    }

    @PostMapping("")
    public ResponseEntity<TodoDto> create(@RequestBody TodoDto todoDto){
        TodoDto insert = todoService.insert(todoDto);
        return ResponseEntity.ok(insert);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TodoDto> update(@PathVariable Long todoId, @RequestBody TodoDto todoDto){
        todoService.update(todoId, todoDto);
        return ResponseEntity.ok(todoDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long todoId){
        todoService.deleteById(todoId);
        return ResponseEntity.noContent().build();
    }

}
