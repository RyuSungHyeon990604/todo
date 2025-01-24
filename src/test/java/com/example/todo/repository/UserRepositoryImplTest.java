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
        mockUser = new User("Test User", "test@example.com",now.toLocalDateTime(),now.toLocalDateTime());
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
    void testUpdateUser() throws InterruptedException {
        Timestamp updateDt = new Timestamp(System.currentTimeMillis());
        User result = userRepository.insert(mockUser);
        long userId = result.getId();
        UserDto dto = new UserDto();
        String updatedName = "Updated User";
        String updatedEmail = "updated@example.com";
        dto.setName(updatedName);
        dto.setEmail(updatedEmail);
        dto.setModDt(updateDt.toLocalDateTime());

        userRepository.update(userId, dto);
        User updatedUser = userRepository.findById(userId);

        assertEquals(updatedName, updatedUser.getName());
        assertEquals(updatedEmail, updatedUser.getEmail());
        System.out.println(updatedUser.getModDt());
        System.out.println(result.getCreateDt());
        assertTrue(updatedUser.getModDt().isAfter(result.getCreateDt()));
    }

    @Test
    @Transactional
    void testFindUserByNonExistentId() {
        assertThrows(RuntimeException.class, () -> userRepository.findById(-1L));
    }
}