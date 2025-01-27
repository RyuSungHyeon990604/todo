package com.example.todo.service;

import com.example.todo.dto.response.UserDto;
import com.example.todo.entity.User;
import com.example.todo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserSvcImpl implements UserService{
    private final UserRepository userRepository;

    public UserSvcImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        //DTO -> Entity
        User user = new User(userDto);
        User insert = userRepository.insert(user);

        return new UserDto(insert);
    }

    @Override
    public int updateUser(Long id, UserDto userDto) {
        int update = userRepository.update(id, userDto);
        return update;
    }
}
