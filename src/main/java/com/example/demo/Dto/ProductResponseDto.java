package com.example.demo.Dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class ProductResponseDto {


    private Long id;

    private String name;

    private Double price;

    private String category;

    private Integer stock;

    private LocalDateTime createdAt;
}
