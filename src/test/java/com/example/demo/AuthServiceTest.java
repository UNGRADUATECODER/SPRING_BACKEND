package com.example.demo;

import com.example.demo.Auth.AuthService;
import com.example.demo.Dto.LoginRequestDto;
import com.example.demo.Dto.LoginResponseDto;
import com.example.demo.Dto.RegisterRequestDto;
import com.example.demo.Dto.RegisterResponseDto;
import com.example.demo.Entity.RefreshToken;
import com.example.demo.Entity.User;
import com.example.demo.Enum.Role;
import com.example.demo.Repository.RefreshTokenRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtService jwtService;
    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Test
    void shouldRegisterUserSuccessfully(){
        RegisterRequestDto request = new RegisterRequestDto();
        request.setUsername("Om");
        request.setEmail("om@gmail.com");
        request.setPassword("123456");
        User savedUser = new User();
       savedUser.setId(1L);
        savedUser.setUsername("Om");
        savedUser.setEmail("om@gmail.com");
        savedUser.setRole(Role.USER);


        when(passwordEncoder.encode("123456"))
                .thenReturn("encodedPassword");
        when(userRepository.save(any(User.class)))
                .thenReturn(savedUser);
        RegisterResponseDto response =
                authService.Create(request);
        assertEquals("Om", response.getUsername());
        assertEquals("om@gmail.com", response.getEmail());
        assertEquals(Role.USER, response.getRole());
        verify(passwordEncoder).encode("123456");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void LoginTest(){
        LoginRequestDto request=new LoginRequestDto();
        request.setEmail("om@gmail.com");
        request.setPassword("12345");
        User user = new User();
        user.setId(1L);
        user.setEmail("om@gmail.com");
        user.setUsername("Om");
        user.setRole(Role.USER);
        when(authenticationManager.authenticate(any()))
                .thenReturn(null);
        when(jwtService.generateToken("om@gmail.com"))
                .thenReturn("accessToken");
        when(jwtService.generateRefreshToken("om@gmail.com"))
                .thenReturn("refreshToken");
        when(userRepository.findByEmail("om@gmail.com"))
                .thenReturn(Optional.of(user));
        when(refreshTokenRepository.save(any(RefreshToken.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        LoginResponseDto response = authService.login(request);
        verify(authenticationManager).authenticate(any());
        verify(jwtService).generateToken("om@gmail.com");
        verify(jwtService).generateRefreshToken("om@gmail.com");
        verify(refreshTokenRepository).save(any(RefreshToken.class));
    }

}