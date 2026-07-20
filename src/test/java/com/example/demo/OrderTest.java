package com.example.demo;

import com.example.demo.Entity.Order;
import com.example.demo.Repository.OrderRepository;
import org.springframework.data.domain.Sort;
import com.example.demo.Repository.ProductRepository;
import org.hibernate.metamodel.mapping.ordering.ast.OrderingExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.sql.ClientInfoStatus;
import java.time.LocalDateTime;
import java.util.List;


@SpringBootTest

public class OrderTest {


    @Autowired
    private OrderRepository orderRepository;
    @Test
    void sortingTest(){

        List<Order> orders =
                orderRepository.findAll(
                        Sort.by("user")
                );

        System.out.println(orders);
    }
//    @Test
//    void create() {
//
//        Order order =Order.builder()
//                .quantity("2")
//                .user("om")
//                .product("car")
//                .build();
//        Order savedProduct= orderRepository.save(order);
//        System.out.println(savedProduct);
//
//
//    }
//    @Test
//    void getRepository(){
//
//        //        List<Order> entites=orderRepository.findByCreatedAt(LocalDateTime.of(2024,1,1,0,0,0));
////        List<Order> entity=orderRepository.findByUserAndProduct("om","car");
//          List<Order> entity=orderRepository.findByUserLike("%o%");
//        System.out.println(entity);}

}