package com.example.demo.Auth;


import com.example.demo.Dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthController {

    public final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public RegisterResponseDto createUser(@Valid @RequestBody RegisterRequestDto request) {
        return authService.Create(request);
    }
    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto request) {
        return authService.login(request);
    }
    @PostMapping("/refresh")
    public RefreshTokenResponseDto refreshToken(
            @RequestBody RefreshTokenRequestDto request) {

        return authService.refreshToken(request);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestBody LogoutRequestDto request) {

        authService.logout(request);

        return ResponseEntity.ok("Logout Successful");
    }

}