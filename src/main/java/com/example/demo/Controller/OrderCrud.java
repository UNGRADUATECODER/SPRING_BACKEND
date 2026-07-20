package com.example.demo.Controller;

import com.example.demo.Dto.OrderRequestDto;
import com.example.demo.Dto.OrderResponseDto;
import com.example.demo.Entity.Order;
import com.example.demo.Service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderCrud {

    private final OrderService orderService;

    public OrderCrud(OrderService orderService) {
        this.orderService = orderService;
    }

    // Create
    @PostMapping("/place-order")
    public OrderResponseDto placeOrder(@RequestBody OrderRequestDto request) {
        return orderService.placeOrder(request);
    }
    @GetMapping("/my-orders")
    public List<OrderResponseDto> getMyOrders() {
        return orderService.getMyOrders();
    }

    // Get All
    @GetMapping
    public List<Order> getAll() {
        return orderService.getAll();
    }

    // Get By Id
    @GetMapping("/{id}")
    public Order getById(@PathVariable Long id) {
        return orderService.getById(id);
    }

    @GetMapping("/product/{productId}")
    public List<OrderResponseDto> getOrdersByProduct(
            @PathVariable Long productId) {

        return orderService.getOrdersByProduct(productId);
    }
    @PutMapping("/{id}")
    public OrderResponseDto updateOrder(
            @PathVariable Long id,
            @RequestBody OrderRequestDto request) {

        return orderService.updateOrder(id, request);
    }

    // Update
//    @PutMapping("/{id}")
//    public ResponseEntity<Order> update(
//            @PathVariable Long id,
//            @RequestBody OrderRequestDto request) {
//
//        return ResponseEntity.ok(orderService.update(id, request));
//    }
//
    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}