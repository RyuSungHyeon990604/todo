package com.example.todo.dto.response;

import com.example.todo.dto.request.UserCreateDto;
import com.example.todo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private Long userId;
    private String name;
    private String email;
    private LocalDateTime modDt;
    public UserDto(User user) {
        this.id = user.getId();
        this.userId = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.modDt = user.getModDt();
    }
    public UserDto(String name, String email) {
        this.name = name;
        this.email = email;
    }
    public UserDto(UserCreateDto userCreateDto) {
        this.name = userCreateDto.getName();
        this.email = userCreateDto.getEmail();
    }
}
