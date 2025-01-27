package com.example.todo.service;

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
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        } catch (DataAccessException e) {
            log.error(e.getMessage(),e);
            throw new TodoNotFoundException("일정을 조회할수없습니다.");
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
    public int deleteById(Long id, TodoDeleteRequestDto requestDto) {
        Todo byId = todoRepo.findById(id);

        if(byId.getUser().getId().equals(requestDto.getUserId())) {
            throw new AccessDeniedException("작성자 아님");
        }
        if(!byId.getPwd().equals(requestDto.getPwd())) {
            throw new InvalidPasswordException();
        }

        return todoRepo.deleteById(id);
    }

    @Override
    @Transactional
    public ResponseTodoDto insert(TodoCreateRequestDto todoDto) {
        //해당 사용자가 존재하면 insert
        User user;

        try {
            user = userRepo.findById(todoDto.getUserId());
        } catch (DataAccessException e) {
            log.error(e.getMessage(),e);
            throw new FailToCreateTodoException("User with id " + todoDto.getUserId() + " not found");
        }

        Todo todo = todoDto.toEntity(user);
        Todo insert;
        try {
            insert = todoRepo.insert(todo);
        } catch (DataAccessException e) {
            log.error(e.getMessage(),e);
            throw new FailToCreateTodoException("fail to create todo");
        }


        ResponseTodoDto res = new ResponseTodoDto(insert);

        return res;
    }

    @Override
    @Transactional
    public int update(Long id, TodoUpdateRequestDto todoDto) {
        Todo byId = todoRepo.findById(id);

        if(!byId.getUser().getId().equals(todoDto.getUserId())) {
            throw new AccessDeniedException("작성자 아님");
        }
        if(!byId.getPwd().equals(todoDto.getPwd())) {
            throw new InvalidPasswordException();
        }

        return todoRepo.update(id, todoDto);
    }
}
