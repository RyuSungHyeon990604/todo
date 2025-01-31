package com.example.todo.controller;

import com.example.todo.dto.ResponseDto;
import com.example.todo.dto.request.UserCreateRequestDto;
import com.example.todo.dto.request.UserUpdateRequestDto;
import com.example.todo.dto.response.ResponseUserDto;
import com.example.todo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<ResponseDto<ResponseUserDto>> createUser(@RequestBody @Valid UserCreateRequestDto createDto) {
        ResponseUserDto responseUserDto = userService.addUser(createDto);
        return ResponseEntity.ok(new ResponseDto<>(responseUserDto,"success"));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> updateUser(@PathVariable Long id, @RequestBody @Valid UserUpdateRequestDto updateDto){
        userService.updateUser(id, updateDto);
        return ResponseEntity.ok(new ResponseDto<>("success"));
    }



}
