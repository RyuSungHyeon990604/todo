package com.example.todo.service;

import com.example.todo.dto.ResponseTodoDto;
import com.example.todo.dto.TodoCreateRequestDto;
import com.example.todo.dto.TodoUpdateRequestDto;
import com.example.todo.entity.Todo;
import com.example.todo.entity.User;
import com.example.todo.exception.DbException;
import com.example.todo.exception.FailToCreateTodoException;
import com.example.todo.exception.TodoNotFoundException;
import com.example.todo.repository.TodoRepository;
import com.example.todo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TodoSvcImpl implements TodoService {
    private final TodoRepository todoRepo;
    private final UserRepository userRepo;

    public TodoSvcImpl(TodoRepository todoRepo, UserRepository userRepo) {
        this.todoRepo = todoRepo;
        this.userRepo = userRepo;
    }

    @Override
    public List<ResponseTodoDto> findAll(Long userId, Long page) {
        List<ResponseTodoDto> res = new ArrayList<>();
        List<Todo> all;

        try {
            all = todoRepo.findAll(userId, page);
        } catch (DbException e) {
            log.error(e.getMessage(),e);
            throw new TodoNotFoundException("TodoNotFoundException");
        }

        //List<Entity> -> List<Dto>
        for (Todo todo : all) {
            res.add(new ResponseTodoDto(todo));
        }

        return res;
    }

    @Override
    public ResponseTodoDto findById(Long id) {
        Todo res = todoRepo.findById(id);
        return new ResponseTodoDto(res);
    }

    @Override
    public int deleteById(Long id) {
        return todoRepo.deleteById(id);
    }

    @Override
    @Transactional
    public ResponseTodoDto insert(TodoCreateRequestDto todoDto) {
        //해당 사용자가 존재하면 insert
        User user;

        try {
            user = userRepo.findById(todoDto.getUserId());
        } catch (DbException e) {
            log.error(e.getMessage(),e);
            throw new FailToCreateTodoException("User with id " + todoDto.getUserId() + " not found");
        }

        Todo todo = todoDto.toEntity(user);
        Todo insert;
        try {
            insert = todoRepo.insert(todo);
        } catch (DbException e) {
            log.error(e.getMessage(),e);
            throw new FailToCreateTodoException("fail to create todo");
        }


        ResponseTodoDto res = new ResponseTodoDto(insert);

        return res;
    }

    @Override
    public int update(Long id, TodoUpdateRequestDto todoDto) {
        return todoRepo.update(id, todoDto);
    }
}
