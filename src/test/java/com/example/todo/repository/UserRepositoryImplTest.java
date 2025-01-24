package com.example.todo.repository;

import com.example.todo.dto.UserDto;
import com.example.todo.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryImplTest {

    @Autowired
    private UserRepository userRepository;

    private User mockUser;

    @BeforeEach
    void setUp() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        mockUser = new User("Test User", "test@example.com",null,now.toLocalDateTime());
    }

    @Test
    @Transactional
    void testInsertUser() {
        Long id = userRepository.insert(mockUser);
        User result = userRepository.findById(id);
        assertNotNull(result);
        assertEquals(mockUser.getName(), result.getName());
        assertEquals(mockUser.getEmail(), result.getEmail());
    }

    @Test
    @Transactional
    void testFindUserById() {
        Long id = userRepository.insert(mockUser);
        User result = userRepository.findById(id);
        User byId = userRepository.findById(result.getId());
        assertEquals(mockUser.getName(), byId.getName());
        assertEquals(mockUser.getEmail(), byId.getEmail());
    }

    @Test
    @Transactional
    void testUpdateUser() {
        Long id = userRepository.insert(mockUser);
        User result = userRepository.findById(id);
        long userId = result.getId();
        String updatedName = "Updated User";
        String updatedEmail = "updated@example.com";

        userRepository.update(userId, updatedName, updatedEmail);
        User updatedUser = userRepository.findById(userId);

        assertEquals(updatedName, updatedUser.getName());
        assertEquals(updatedEmail, updatedUser.getEmail());
        assertEquals(result.getCreateDt(), updatedUser.getCreateDt());
    }

    @Test
    @Transactional
    void testFindUserByNonExistentId() {
        assertThrows(RuntimeException.class, () -> userRepository.findById(-1L));
    }
}