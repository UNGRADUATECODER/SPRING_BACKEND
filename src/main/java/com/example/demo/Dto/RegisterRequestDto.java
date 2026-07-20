package com.example.demo.Dto;

import com.example.demo.Enum.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDto {
    @NotBlank(message = "name should not be blank")
    private String username;
    @Email
    private String email;
    @NotBlank
    private String password;

    private Role role;

}
