package com.example.todo.controller;

import com.example.todo.dto.UserCreateDto;
import com.example.todo.dto.UserDto;
import com.example.todo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserCreateDto createDto) {
        UserDto userDto = userService.addUser(new UserDto(createDto));
        return ResponseEntity.ok(userDto);
    }

}
