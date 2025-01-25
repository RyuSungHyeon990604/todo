package com.example.todo.service;

import com.example.todo.dto.TodoDto;
import com.example.todo.entity.Todo;
import com.example.todo.entity.User;
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
    public List<TodoDto> findAll(Long userId, Long page) {
        List<TodoDto> res = new ArrayList<>();
        List<Todo> all = todoRepo.findAll(userId, page);

        //List<Entity> -> List<Dto>
        for (Todo todo : all) {
            res.add(new TodoDto(todo));
        }

        return res;
    }

    @Override
    public TodoDto findById(Long id) {
        Todo res = todoRepo.findById(id);
        return new TodoDto(res);
    }

    @Override
    public int deleteById(Long id) {
        return todoRepo.deleteById(id);
    }

    @Override
    @Transactional
    public TodoDto insert(TodoDto todoDto) {
        //해당 사용자가 존재하면 insert
        User user = userRepo.findById(todoDto.getUserId());
        Todo todo = new Todo(user,todoDto);

        Todo insert = todoRepo.insert(todo);

        TodoDto res = new TodoDto(insert);

        return res;
    }

    @Override
    public int update(Long id, TodoDto todoDto) {
        return todoRepo.update(id, todoDto);
    }
}
