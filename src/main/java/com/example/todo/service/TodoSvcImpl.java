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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TodoSvcImpl implements TodoService {
    private final TodoRepository todoRepo;
    private final UserRepository userRepo;

    public TodoSvcImpl(@Qualifier("todoRepositoryImpl") TodoRepository todoRepo, UserRepository userRepo) {
        this.todoRepo = todoRepo;
        this.userRepo = userRepo;
    }

    @Override
    public List<ResponseTodoDto> findAll(Long userId, Long page, Long pageSize, LocalDate date) {
        List<ResponseTodoDto> res = new ArrayList<>();
        List<Todo> all = todoRepo.findAll(userId, page, pageSize, date);

        //List<Entity> -> List<Dto>
        for (Todo todo : all) {
            res.add(new ResponseTodoDto(todo));
        }

        return res;
    }

    @Override
    public ResponseTodoDto findById(Long todoId) {

        Todo byId = todoRepo.findById(todoId).orElseThrow(TodoNotFoundException::new);

        return new ResponseTodoDto(byId);
    }

    @Override
    @Transactional
    public int deleteById(Long userId, Long todoId, TodoDeleteRequestDto requestDto) {
        Todo byId = todoRepo.findById(todoId).orElseThrow(TodoNotFoundException::new);

        if(!byId.getUser().getId().equals(userId)) {
            throw new AccessDeniedException();
        }
       validatePassword(byId, requestDto.getPwd());

        return todoRepo.deleteById(todoId);
    }

    @Override
    @Transactional
    public ResponseTodoDto insert(Long userId, TodoCreateRequestDto requestDto) {
        //해당 사용자가 존재하면 insert
        User user = userRepo.findById(userId).orElseThrow(UserNotFoundException::new);
        Todo todo = requestDto.toEntity(user);

        Todo insert = todoRepo.insert(todo);

        return new ResponseTodoDto(insert);
    }

    @Override
    @Transactional
    public int update(Long userId, Long todoId, TodoUpdateRequestDto requestDto) {
        Todo byId = todoRepo.findById(todoId).orElseThrow(TodoNotFoundException::new);

        if(!byId.getUser().getId().equals(userId)) {
            throw new AccessDeniedException();
        }

        validatePassword(byId, requestDto.getPwd());

        return todoRepo.update(todoId, requestDto);
    }

    private void validatePassword(Todo todo, String inputPassword){
        if(!todo.getPwd().equals(inputPassword)) {
            throw new InvalidPasswordException();
        }
    }
}
