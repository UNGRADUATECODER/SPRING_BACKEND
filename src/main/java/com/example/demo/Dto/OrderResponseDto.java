package com.example.demo.Dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponseDto {
    private Long orderId;

    private String userName;

    private String productName;

    private Integer quantity;
}
