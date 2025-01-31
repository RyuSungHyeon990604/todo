package com.example.todo.service;

import com.example.todo.code.ErrorCode;
import com.example.todo.dto.request.TodoDeleteRequestDto;
import com.example.todo.dto.response.ResponseTodoDto;
import com.example.todo.dto.request.TodoCreateRequestDto;
import com.example.todo.dto.request.TodoUpdateRequestDto;
import com.example.todo.entity.Todo;
import com.example.todo.entity.User;
import com.example.todo.exception.*;
import com.example.todo.repository.TodoRepository;
import com.example.todo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TodoSvcImpl implements TodoService {
    private final TodoRepository todoRepo;
    private final UserRepository userRepo;

    public TodoSvcImpl(@Qualifier("todoRepositoryImpl") TodoRepository todoRepo, UserRepository userRepo) {
        this.todoRepo = todoRepo;
        this.userRepo = userRepo;
    }

    @Override
    public List<ResponseTodoDto> findAll(Long userId, Long page) {
        List<ResponseTodoDto> res = new ArrayList<>();
        List<Todo> all = todoRepo.findAll(userId, page);

        //List<Entity> -> List<Dto>
        for (Todo todo : all) {
            res.add(new ResponseTodoDto(todo));
        }

        return res;
    }

    @Override
    public ResponseTodoDto findById(Long todoId) {

        Todo byId = todoRepo.findById(todoId).orElseThrow(()->new TodoNotFoundException());

        return new ResponseTodoDto(byId);
    }

    @Override
    @Transactional
    public int deleteById(Long userId, Long todoId, TodoDeleteRequestDto requestDto) {
        Todo byId = todoRepo.findById(todoId).orElseThrow(()->new TodoNotFoundException());

        if(!byId.getUser().getId().equals(userId)) {
            throw new AccessDeniedException();
        }
        if(!byId.getPwd().equals(requestDto.getPwd())) {
            throw new InvalidPasswordException();
        }

        return todoRepo.deleteById(todoId);
    }

    @Override
    @Transactional
    public ResponseTodoDto insert(Long userId, TodoCreateRequestDto requestDto) {
        //해당 사용자가 존재하면 insert
        User user = userRepo.findById(userId);

        Todo todo = requestDto.toEntity(user);
        Todo insert;
        try {
            insert = todoRepo.insert(todo);
        } catch (DataAccessException e) {
            log.error(e.getMessage(),e);
            throw new FailToCreateTodoException(ErrorCode.DB_ERROR);
        }

        return new ResponseTodoDto(insert);
    }

    @Override
    @Transactional
    public int update(Long userId, Long todoId, TodoUpdateRequestDto requestDto) {
        Todo byId = todoRepo.findById(todoId).orElseThrow(()->new TodoNotFoundException());

        if(!byId.getUser().getId().equals(userId)) {
            throw new AccessDeniedException();
        }
        if(!byId.getPwd().equals(requestDto.getPwd())) {
            throw new InvalidPasswordException();
        }

        return todoRepo.update(todoId, requestDto);
    }
}
