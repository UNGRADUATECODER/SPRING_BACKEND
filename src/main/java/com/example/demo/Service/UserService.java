package com.example.demo.Service;

import com.example.demo.Dto.*;
import com.example.demo.Entity.Order;
import com.example.demo.Entity.User;
import com.example.demo.Exception.UserNotFoundException;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private  final OrderRepository orderRepository;
    public UserService(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }




    public  UserResponseDto Create(UserRequestDto request){

        User user=new User();
       user.setUsername(request.getUsername());
       user.setEmail(request.getEmail());
       user.setPassword(request.getPassword());
       user.setRole(request.getRole());
       User savedUser=userRepository.save(user);

       UserResponseDto userResponseDto=new UserResponseDto();
          userResponseDto.setEmail(savedUser.getEmail());
          userResponseDto.setUsername(savedUser.getUsername());
        userResponseDto.setId(savedUser.getId());
        userResponseDto.setRole(savedUser.getRole());

        return userResponseDto;

    }
    public List<OrderResponseDto> getOrderDetail(Long id){
         userRepository.findById(id).orElseThrow(()->new UserNotFoundException("user not found"));
         List<Order> orders=orderRepository.findByUserId(id);
        return orders.stream().map(order -> {

            OrderResponseDto dto = new OrderResponseDto();

            dto.setOrderId(order.getId());
            dto.setProductName(order.getProduct().getName());
            dto.setUserName(order.getUser().getUsername());
            dto.setQuantity(order.getQuantity());

            return dto;

        }).toList();

    }
    public List<UserResponseDto> getElement(){
           List<User> user=userRepository.findAll();
        return user.stream().map(users -> {

            UserResponseDto dto=new UserResponseDto();
dto.setId(users.getId());
dto.setUsername(users.getUsername());
dto.setRole(users.getRole());
dto.setEmail(users.getEmail());
            return dto;

        }).toList();
    }

    public UserResponseDto getUserById(Long id){

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserResponseDto dto = new UserResponseDto();

        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());

        return dto;
    }

    public UserResponseDto updateUser(Long id, UserRequestDto request){

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        User saved= userRepository.save(user);
         UserResponseDto responseDto = new UserResponseDto();
          responseDto.setUsername(saved.getUsername());
          responseDto.setEmail(saved.getEmail());
          return responseDto;
    }
    public void deleteUser(Long id){

        if(!userRepository.existsById(id)){
            throw new RuntimeException("User not found");
        }

        userRepository.deleteById(id);
    }
}
