package com.example.todo.service;

import com.example.todo.dto.TodoDto;
import com.example.todo.entity.Todo;
import com.example.todo.entity.User;
import com.example.todo.repository.TodoRepository;
import com.example.todo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoSvcImplTest {
    @Mock
    private TodoRepository todoRepo;

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private TodoSvcImpl todoSvc;

    User user;

    @BeforeEach
    void setUp() {
        user = new User("User1","990@gamil.com");
    }

    @Test
    void findAll_ShouldReturnTodoDtoList() {
        // given
        Todo todo1 = new Todo(1L, user, "Todo 1", "pwd1", LocalDateTime.now(), LocalDateTime.now());
        Todo todo2 = new Todo(2L, user, "Todo 2", "pwd2", LocalDateTime.now(), LocalDateTime.now());
        when(todoRepo.findAll()).thenReturn(Arrays.asList(todo1, todo2));

        // when
        List<TodoDto> result = todoSvc.findAll();

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getTodo()).isEqualTo("Todo 1");
        verify(todoRepo, times(1)).findAll();
    }

    @Test
    void findById_ShouldReturnTodoDto() {
        // given
        Long id = 1L;
        Todo todo2 = new Todo(id, user, "Test Todo", "pwd", LocalDateTime.now(), LocalDateTime.now());

        when(todoRepo.findById(id)).thenReturn(todo2);

        // when
        TodoDto result = todoSvc.findById(id);

        // then
        assertThat(result.getTodo()).isEqualTo("Test Todo");
        verify(todoRepo, times(1)).findById(id);
    }

    @Test
    void deleteById_ShouldCallRepository() {
        // given
        Long id = 1L;
        when(todoRepo.deleteById(id)).thenReturn(1);

        // when
        todoSvc.deleteById(id);

        // then
        verify(todoRepo, times(1)).deleteById(id);
    }

    @Test
    void insert_ShouldInsertAndReturnTodoDto() {
        // given
        Long userId = 1L;
        TodoDto todoDto = new TodoDto();
        todoDto.setTodo("Todo 1");
        User user = new User(userId, "User 1", "user1@example.com", LocalDateTime.now(), LocalDateTime.now());
        Todo todo = new Todo(null, user, "New Todo", "pwd", LocalDateTime.now(), LocalDateTime.now());
        Todo insertedTodo = new Todo(1L, user, "New Todo", "pwd", LocalDateTime.now(), LocalDateTime.now());

        when(userRepo.findById(userId)).thenReturn(user);
        when(todoRepo.insert(any(Todo.class))).thenReturn(insertedTodo);

        // when
        TodoDto result = todoSvc.insert(userId, todoDto);

        // then
        assertThat(result.getTodo()).isEqualTo("New Todo");
        verify(userRepo, times(1)).findById(userId);
        verify(todoRepo, times(1)).insert(any(Todo.class));
    }

    @Test
    void update_ShouldUpdateTodo() {
        // given
        Long id = 1L;
        TodoDto todoDto = new TodoDto();
        todoDto.setTodo("Update Todo");

        when(todoRepo.update(id, todoDto)).thenReturn(1);

        // when
        todoSvc.update(id, todoDto);

        // then
        verify(todoRepo, times(1)).update(id, todoDto);
    }

    @Test
    void insert_ShouldThrowExceptionWhenUserNotFound() {
        // given
        Long userId = 1L;
        TodoDto todoDto = new TodoDto();
        todoDto.setTodo("Insert Todo");
        when(userRepo.findById(userId)).thenThrow(new RuntimeException("User not found"));

        // when & then
        assertThrows(RuntimeException.class, () -> todoSvc.insert(userId, todoDto));
        verify(userRepo, times(1)).findById(userId);
        verify(todoRepo, times(0)).insert(any(Todo.class));
    }
}