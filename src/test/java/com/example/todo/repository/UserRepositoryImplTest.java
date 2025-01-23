package com.example.todo.repository;

import com.example.todo.dto.UserDto;
import com.example.todo.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryImplTest {

    @Autowired
    private UserRepository userRepository;

    private UserDto mockUser;

    @BeforeEach
    void setUp() {
        mockUser = new UserDto(0, "Test User", "test@example.com");
    }

    @Test
    @Transactional
    void testInsertUser() {
        User result = userRepository.insert(mockUser);
        assertNotNull(result);
        assertEquals(mockUser.getName(), result.getName());
        assertEquals(mockUser.getEmail(), result.getEmail());
    }

    @Test
    @Transactional
    void testFindUserById() {
        User result = userRepository.insert(mockUser);
        User byId = userRepository.findById(result.getId());
        assertEquals(mockUser.getName(), byId.getName());
        assertEquals(mockUser.getEmail(), byId.getEmail());
    }

    @Test
    @Transactional
    void testUpdateUser() {
        User result = userRepository.insert(mockUser);
        long userId = result.getId();
        String updatedName = "Updated User";
        String updatedEmail = "updated@example.com";

        userRepository.update(userId, updatedName, updatedEmail);
        User updatedUser = userRepository.findById(userId);

        assertEquals(updatedName, updatedUser.getName());
        assertEquals(updatedEmail, updatedUser.getEmail());
    }

    @Test
    @Transactional
    void testFindUserByNonExistentId() {
        assertThrows(RuntimeException.class, () -> userRepository.findById(-1L));
    }
}