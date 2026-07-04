package com.example.demo.Dto;


import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {

    @NotBlank(message = "name should not be blank")
    private String username;
    @Email
    private String email;
    @NotBlank
    private String password;

    private String role;

}
