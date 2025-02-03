package com.example.todo.service;

import com.example.todo.code.ErrorCode;
import com.example.todo.dto.request.UserCreateRequestDto;
import com.example.todo.dto.request.UserUpdateRequestDto;
import com.example.todo.dto.response.ResponseUserDto;
import com.example.todo.entity.User;
import com.example.todo.exception.FailToCreateException;
import com.example.todo.exception.UserNotFoundException;
import com.example.todo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class UserSvcImpl implements UserService {
    private final UserRepository userRepository;

    public UserSvcImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public ResponseUserDto addUser(UserCreateRequestDto userDto) {//DTO -> Entity
        User user = userDto.toEntity();

        Optional<User> insert = userRepository.insert(user);
        if (insert.isEmpty()) {
            throw new FailToCreateException(ErrorCode.USER_NOT_FOUND);
        }
        return new ResponseUserDto(insert.get());

    }

    @Override
    public int updateUser(Long id, UserUpdateRequestDto userDto) {
        int update = userRepository.update(id, userDto);

        if (update == 0) {
            throw new UserNotFoundException();
        }
        return 1;
    }
}
