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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TodoSvcImpl implements TodoService {
    private final TodoRepository todoRepo;
    private final UserRepository userRepo;

    public TodoSvcImpl(@Qualifier("namedParameterJdbcTodoRepositoryImpl") TodoRepository todoRepo, UserRepository userRepo) {
        this.todoRepo = todoRepo;
        this.userRepo = userRepo;
    }

    @Override
    public List<ResponseTodoDto> findAll(Long userId, Long page) {
        List<ResponseTodoDto> res = new ArrayList<>();
        List<Todo> all;

        try {
            all = todoRepo.findAll(userId, page);
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage(),e);
            throw new TodoNotFoundException("등록된 일정이 없습니다.");
        }

        //List<Entity> -> List<Dto>
        for (Todo todo : all) {
            res.add(new ResponseTodoDto(todo));
        }

        return res;
    }

    @Override
    public ResponseTodoDto findById(Long id) {
        try {
            Todo res = todoRepo.findById(id);
            return new ResponseTodoDto(res);
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage(),e);
            throw new TodoNotFoundException("일정 "+ id + " 이 존재하지않습니다.");
        }

    }

    @Override
    @Transactional
    public int deleteById(Long id, TodoDeleteRequestDto requestDto) {
        Todo byId = todoRepo.findById(id);

        if(byId.getUser().getId().equals(requestDto.getUserId())) {
            throw new AccessDeniedException("일정을 등록한 사용자가 아닙니다.");
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
        User user = userRepo.findById(todoDto.getUserId());

        Todo todo = todoDto.toEntity(user);
        Todo insert;
        try {
            insert = todoRepo.insert(todo);
        } catch (DataAccessException e) {
            log.error(e.getMessage(),e);
            throw new FailToCreateTodoException("일정등록 실패");
        }


        ResponseTodoDto res = new ResponseTodoDto(insert);

        return res;
    }

    @Override
    @Transactional
    public int update(Long id, TodoUpdateRequestDto todoDto) {
        ResponseTodoDto byId = findById(id);

        if(!byId.getUserId().equals(todoDto.getUserId())) {
            throw new AccessDeniedException("일정을 등록한 사용자가 아닙니다.");
        }
        if(!byId.getPwd().equals(todoDto.getPwd())) {
            throw new InvalidPasswordException();
        }

        return todoRepo.update(id, todoDto);
    }
}
