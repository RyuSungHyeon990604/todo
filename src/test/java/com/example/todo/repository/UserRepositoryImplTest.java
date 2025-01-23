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

    private User mockUser;

    @BeforeEach
    void setUp() {
        mockUser = new User(0, "Test User", "test@example.com", Date.valueOf("2023-01-01"), Date.valueOf("2023-01-02"));
    }

    @Test
    @Transactional
    void testInsertUser() {
        UserDto result = userRepository.insert(mockUser);
        assertNotNull(result);
        assertEquals(mockUser.getName(), result.getName());
        assertEquals(mockUser.getEmail(), result.getEmail());
    }

    @Test
    @Transactional
    void testFindUserById() {
        UserDto result = userRepository.insert(mockUser);
        Optional<UserDto> byId = userRepository.findById(result.getId());
        assertTrue(byId.isPresent());
        assertEquals(mockUser.getName(), byId.get().getName());
        assertEquals(mockUser.getEmail(), byId.get().getEmail());
    }

    @Test
    @Transactional
    void testUpdateUser() {
        UserDto result = userRepository.insert(mockUser);
        long userId = result.getId();
        String updatedName = "Updated User";
        String updatedEmail = "updated@example.com";

        userRepository.update(userId, updatedName, updatedEmail);
        Optional<UserDto> updatedUser = userRepository.findById(userId);

        assertTrue(updatedUser.isPresent());
        assertEquals(updatedName, updatedUser.get().getName());
        assertEquals(updatedEmail, updatedUser.get().getEmail());
    }

    @Test
    @Transactional
    void testFindUserByNonExistentId() {
        Optional<UserDto> byId = userRepository.findById(-1L);
        assertTrue(byId.isEmpty());
    }
}