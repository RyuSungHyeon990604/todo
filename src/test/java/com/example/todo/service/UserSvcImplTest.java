package com.example.todo.service;

import com.example.todo.dto.response.UserDto;
import com.example.todo.entity.User;
import com.example.todo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserSvcImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserSvcImpl userSvcImpl;

    private UserDto userDto;

    @BeforeEach
    void setUp() {
        // given: test data
        userDto = new UserDto();
        userDto.setName("John");
        userDto.setEmail("john@example.com");
    }

    @Test
    void addUser_ShouldReturnUserDto() {
        // given: mock behavior
        User user = new User(1L, userDto.getName(), userDto.getEmail(), null, null);
        when(userRepository.insert(any(User.class))).thenReturn(user);

        // when: call service method
        UserDto result = userSvcImpl.addUser(userDto);

        // then: assert results
        assertNotNull(result);
        assertEquals(userDto.getName(), result.getName());
        assertEquals(userDto.getEmail(), result.getEmail());
        verify(userRepository, times(1)).insert(any(User.class)); // verify interaction
    }

    @Test
    void updateUser_ShouldReturnUpdatedCount() {
        // given: mock behavior
        when(userRepository.update(anyLong(), any(UserDto.class))).thenReturn(1);

        // when: call service method
        int result = userSvcImpl.updateUser(1L, userDto);

        // then: assert results
        assertEquals(1, result);
        verify(userRepository, times(1)).update(eq(1L), any(UserDto.class)); // verify interaction
    }
}