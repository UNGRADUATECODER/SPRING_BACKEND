package com.example.demo.Auth;

import com.example.demo.Dto.*;
import com.example.demo.Entity.RefreshToken;
import com.example.demo.Entity.User;
import com.example.demo.Enum.Role;
import com.example.demo.Repository.RefreshTokenRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Security.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class AuthService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RegisterResponseDto Create(RegisterRequestDto request){
        log.info("Registration request received for {}", request.getEmail());
        User user=new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        User savedUser=userRepository.save(user);

        RegisterResponseDto registerResponseDto=new RegisterResponseDto();
        registerResponseDto.setEmail(savedUser.getEmail());
        registerResponseDto.setUsername(savedUser.getUsername());
        registerResponseDto.setId(savedUser.getId());
        registerResponseDto.setRole(savedUser.getRole());

        return registerResponseDto;

    }
    public LoginResponseDto login(LoginRequestDto request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            System.out.println("Authentication Success");

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        String accessToken = jwtService.generateToken(request.getEmail());

        String refreshToken = jwtService.generateRefreshToken(request.getEmail());
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .token(refreshToken)
                .user(user)
                .expiryDate(LocalDateTime.now().plusDays(7))
                .revoked(false)
                .build();

        refreshTokenRepository.save(refreshTokenEntity);
        LoginResponseDto response = new LoginResponseDto();
        response.setToken(accessToken);
        response.setRefreshToken(refreshToken);
        return response;
    }
    public RefreshTokenResponseDto refreshToken(
            RefreshTokenRequestDto request) {
        String refreshToken = request.getRefreshToken();
        String email = jwtService.validateRefreshToken(refreshToken);
        RefreshToken savedToken=refreshTokenRepository.findByToken(refreshToken).orElseThrow(()->new RuntimeException("refresh token not found"));
        if (savedToken.isRevoked()) {
            throw new RuntimeException("Refresh Token Revoked");
        }
        if (savedToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh Token Expired");
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));




        String newAccessToken = jwtService.generateToken(user.getEmail());
        RefreshTokenResponseDto response =
                new RefreshTokenResponseDto();
        response.setAccessToken(newAccessToken);
        return response;
    }
    public void logout(LogoutRequestDto request){
        RefreshToken refreshToken =
                refreshTokenRepository.findByToken(request.getRefreshToken())
                        .orElseThrow(()->new RuntimeException("not found"));
        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
    }
}
