package com.example.demo.Controller;

import com.example.demo.Dto.OrderResponseDto;
import com.example.demo.Dto.UserRequestDto;
import com.example.demo.Dto.UserResponseDto;
import com.example.demo.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserCrud {

    private final UserService userService;

    public UserCrud(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/{id}/orders")
    public List<OrderResponseDto> getOrder( @Valid @PathVariable Long id){
        return  userService.getOrderDetail(id);
    }

    @PostMapping
    public UserResponseDto createUser(@Valid @RequestBody UserRequestDto request) {
        return userService.Create(request);
    }

    @GetMapping
    public List<UserResponseDto> getAllUser() {
        return userService.getElement();
    }

    @GetMapping("/{id}")
    public UserResponseDto getUserById(@Valid  @PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public UserResponseDto update(@Valid
            @PathVariable Long id,
            @RequestBody UserRequestDto requestDto) {

        return userService.updateUser(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@Valid @PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.ok("User deleted successfully");
    }
}