package com.example.todo.service;

import com.example.todo.dto.request.UserCreateRequestDto;
import com.example.todo.dto.request.UserUpdateRequestDto;
import com.example.todo.dto.response.ResponseUserDto;
import com.example.todo.entity.User;
import com.example.todo.exception.DuplicateKeyException;
import com.example.todo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserSvcImpl implements UserService{
    private final UserRepository userRepository;

    public UserSvcImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public ResponseUserDto addUser(UserCreateRequestDto userDto) {
        String inputEmail = userDto.getEmail();
        if(userRepository.isDuplicate("email", inputEmail)) {
            throw new DuplicateKeyException("이미 사용중인 이메일입니다 :" + inputEmail);
        }
        //DTO -> Entity
        User user = userDto.toEntity();
        User insert = userRepository.insert(user);

        return new ResponseUserDto(insert);
    }

    @Override
    public int updateUser(Long id, UserUpdateRequestDto userDto) {
        String inputEmail = userDto.getEmail();
        User byId = userRepository.findById(id);
        if(byId.getEmail().equals(inputEmail) && byId.getName().equals(userDto.getName())) {
            return 0;
        }
        //이메일을 변경하지않았으면 중복체크는 하지않는다.
        if(!byId.getEmail().equals(inputEmail) && userRepository.isDuplicate("email", inputEmail)) {
            throw new DuplicateKeyException("이미 사용중인 이메일입니다.");
        }
        return userRepository.update(id, userDto);
    }
}
