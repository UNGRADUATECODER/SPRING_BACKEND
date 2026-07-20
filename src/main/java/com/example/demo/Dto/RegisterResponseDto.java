package com.example.demo.Dto;

import com.example.demo.Enum.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterResponseDto {

    private Long id;
    private String username;
    private String email;
    private Role role;

}
