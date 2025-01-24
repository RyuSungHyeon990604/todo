package com.example.todo.repository;

import com.example.todo.dto.TodoDto;
import com.example.todo.entity.Todo;
import com.example.todo.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TodoRepositoryImplTest {
    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    private Todo todo;
    private Todo todoNull = new Todo(null,null,null,null,null,null);
    private Long userId;

    @BeforeEach
    @Transactional
    void setUp() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        User user = new User("Test User", "test@example.com", null, now.toLocalDateTime());
        User insert = userRepository.insert(user);
        userId = insert.getId();
        todo = new Todo(null, insert.getId(), "할일", "1234", now.toLocalDateTime(), now.toLocalDateTime());
    }

    @Test
    @Transactional
    void testInsertTodo() {
        Todo insert = todoRepository.insert(todo);
        assertEquals("할일", insert.getTodo());
        assertEquals("1234", insert.getPwd());
        assertNotNull(insert.getId());
        assertEquals(userId, insert.getUserId());
    }

    @Test
    @Transactional
    void testInsertNullTodo() {
        assertThrows(IllegalArgumentException.class, ()->todoRepository.insert(todoNull));
        assertThrows(NullPointerException.class, ()->todoRepository.insert(null));
    }

    @Test
    @Transactional
    void testFindAll() {
        todoRepository.insert(todo);
        todoRepository.insert(new Todo(null, userId, "다른 할일", "5678", todo.getCreateDt(), todo.getModDt()));

        List<Todo> todos = todoRepository.findAll();

        assertFalse(todos.isEmpty());
        assertTrue(todos.stream().anyMatch(t -> t.getTodo().equals("할일")));
        assertTrue(todos.stream().anyMatch(t -> t.getTodo().equals("다른 할일")));
    }


    @Test
    @Transactional
    void testFindAllByUserId() {
        todoRepository.insert(todo);
        todoRepository.insert(new Todo(null, userId, "다른 할일", "5678", todo.getCreateDt(), todo.getModDt()));

        List<Todo> todos = todoRepository.findAllByUserId(userId);

        assertFalse(todos.isEmpty());
        assertTrue(todos.stream().allMatch(t -> t.getUserId().equals(userId)));
    }

    @Test
    @Transactional
    void testDeleteById() {
        Todo insert = todoRepository.insert(todo);

        int rowsAffected = todoRepository.deleteById(insert.getId());
        assertEquals(1, rowsAffected);

        List<Todo> todos = todoRepository.findAll();
        assertTrue(todos.stream().noneMatch(t -> t.getId().equals(insert.getId())));
    }

    @Test
    @Transactional
    void testUpdateTodo() throws InterruptedException {
        Todo insert = todoRepository.insert(todo);

        Todo find = todoRepository.findById(insert.getId());

        Thread.sleep(1000);
        Timestamp now2 = new Timestamp(System.currentTimeMillis());
        String updatedStr = "업데이트된 할일";
        TodoDto todoDto = new TodoDto();
        todoDto.setTodo(updatedStr);
        todoDto.setModDt(now2.toLocalDateTime());

        int rowsAffected = todoRepository.update(find.getId(), todoDto);
        Todo updated = todoRepository.findById(find.getId());

        assertEquals(1, rowsAffected);
        assertNotNull(updated);
        assertEquals(updatedStr, updated.getTodo());
        assertTrue(updated.getModDt().isAfter(find.getCreateDt()));
        assertEquals(find.getCreateDt(), updated.getCreateDt());
    }
}