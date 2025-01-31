package com.example.todo.service;

import com.example.todo.code.ErrorCode;
import com.example.todo.dto.request.UserCreateRequestDto;
import com.example.todo.dto.request.UserUpdateRequestDto;
import com.example.todo.dto.response.ResponseUserDto;
import com.example.todo.entity.User;
import com.example.todo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        try {
            User insert = userRepository.insert(user);
            return new ResponseUserDto(insert);
        } catch (DuplicateKeyException e) {
            log.error(e.getMessage(),e);
            throw e;
        }
    }

    @Override
    public int updateUser(Long id, UserUpdateRequestDto userDto) {
        return userRepository.update(id, userDto);
    }
}
