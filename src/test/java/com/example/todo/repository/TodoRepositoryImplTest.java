package com.example.todo.repository;

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
    private Long userId;

    @BeforeEach
    @Transactional
    void setUp() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        User user = new User("Test User", "test@example.com", null, now.toLocalDateTime());
        userId = userRepository.insert(user);
        todo = new Todo(null, null, "할일", "1234", now.toLocalDateTime(), now.toLocalDateTime());
    }

    @Test
    @Transactional
    void testInsertTodo() {
        Long id = todoRepository.insert(userId, todo);
        Todo insert = todoRepository.findById(id);
        assertEquals("할일", insert.getTodo());
        assertEquals("1234", insert.getPwd());
        assertNotNull(insert.getId());
        assertEquals(userId, insert.getUserId());
    }

    @Test
    @Transactional
    void testFindAll() {
        todoRepository.insert(userId, todo);
        todoRepository.insert(userId, new Todo(null, null, "다른 할일", "5678", todo.getCreateDt(), todo.getModDt()));

        List<Todo> todos = todoRepository.findAll();

        assertFalse(todos.isEmpty());
        assertTrue(todos.stream().anyMatch(t -> t.getTodo().equals("할일")));
        assertTrue(todos.stream().anyMatch(t -> t.getTodo().equals("다른 할일")));
    }


    @Test
    @Transactional
    void testFindAllByUserId() {
        todoRepository.insert(userId, todo);
        todoRepository.insert(userId, new Todo(null, null, "다른 할일", "5678", todo.getCreateDt(), todo.getModDt()));

        List<Todo> todos = todoRepository.findAllByUserId(userId);

        assertFalse(todos.isEmpty());
        assertTrue(todos.stream().allMatch(t -> t.getUserId().equals(userId)));
    }

    @Test
    @Transactional
    void testDeleteById() {
        Long id = todoRepository.insert(userId, todo);

        int rowsAffected = todoRepository.deleteById(id);
        assertEquals(1, rowsAffected);

        List<Todo> todos = todoRepository.findAll();
        assertTrue(todos.stream().noneMatch(t -> t.getId().equals(id)));
    }

    @Test
    @Transactional
    void testUpdateTodo() throws InterruptedException {
        Long id = todoRepository.insert(userId, todo);
        Todo insert = todoRepository.findById(id);
        Thread.sleep(1000);
        Timestamp now2 = new Timestamp(System.currentTimeMillis());
        String updatedStr = "업데이트된 할일";
        Todo updatedTodo = new Todo(null,null,updatedStr, "5678", insert.getCreateDt(), now2.toLocalDateTime());


        int rowsAffected = todoRepository.update(id, updatedTodo);
        assertEquals(1, rowsAffected);

        Todo updated = todoRepository.findById(id);
        assertNotNull(updated);
        assertEquals(updatedStr, updated.getTodo());
        assertTrue(updated.getModDt().isAfter(insert.getCreateDt()));
        assertEquals(insert.getCreateDt(), updated.getCreateDt());
    }
}