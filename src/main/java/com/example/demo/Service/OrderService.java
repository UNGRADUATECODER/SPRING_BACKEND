package com.example.demo.Service;

import com.example.demo.Dto.OrderRequestDto;
import com.example.demo.Dto.OrderResponseDto;
import com.example.demo.Entity.Order;
import com.example.demo.Entity.Product;
import com.example.demo.Entity.User;
import com.example.demo.Exception.OrderNotFoundException;
import com.example.demo.Exception.ProductNotFoundException;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }



    // Create
    public OrderResponseDto placeOrder(OrderRequestDto request) {


              User user= userRepository.findById(request.getUserId()).orElseThrow(()->new RuntimeException("user not found"));

        Product product=productRepository.findById(request.getProductId()).orElseThrow(()->new RuntimeException("product not found"));

         if(product.getStock()< request.getQuantity()){
             throw new RuntimeException("out of stock");
         }
        product.setStock(
                product.getStock() - request.getQuantity()
        );
        productRepository.save(product);
        Order order = new Order();
        order.setUser(user);
        order.setProduct(product);
        order.setQuantity(request.getQuantity());
        Order savedOrder =
                orderRepository.save(order);
        OrderResponseDto response =
                new OrderResponseDto();
        response.setOrderId(savedOrder.getId());
        response.setUserName(
                savedOrder.getUser().getUsername()
        );
        response.setQuantity(
                savedOrder.getQuantity()
        );
        response.setProductName(
                savedOrder.getProduct().getName()
        );
        return response;

    }
    public List<OrderResponseDto> getOrdersByUser(Long userId) {

        List<Order> orders = orderRepository.findByUserId(userId);

        return orders.stream().map(order -> {

            OrderResponseDto dto = new OrderResponseDto();

            dto.setOrderId(order.getId());
            dto.setUserName(order.getUser().getUsername());
            dto.setProductName(order.getProduct().getName());
            dto.setQuantity(order.getQuantity());

            return dto;

        }).toList();
    }


    public List<OrderResponseDto> getOrdersByProduct(Long productId) {

        List<Order> orders = orderRepository.findByProductId(productId);

        return orders.stream().map(order -> {

            OrderResponseDto dto = new OrderResponseDto();

            dto.setOrderId(order.getId());
            dto.setUserName(order.getUser().getUsername());
            dto.setProductName(order.getProduct().getName());
            dto.setQuantity(order.getQuantity());

            return dto;

        }).toList();
    }
    public OrderResponseDto updateOrder(Long id,
                                        OrderRequestDto request) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new OrderNotFoundException("Order not found"));

        Product product = order.getProduct();

        int oldQuantity = order.getQuantity();
        int newQuantity = request.getQuantity();

        int difference = newQuantity - oldQuantity;

        if (difference > 0 && product.getStock() < difference) {
            throw new ProductNotFoundException("Out of Stock");
        }

        product.setStock(product.getStock() - difference);

        productRepository.save(product);

        order.setQuantity(newQuantity);

        Order savedOrder = orderRepository.save(order);

        OrderResponseDto dto = new OrderResponseDto();

        dto.setOrderId(savedOrder.getId());
        dto.setUserName(savedOrder.getUser().getUsername());
        dto.setProductName(savedOrder.getProduct().getName());
        dto.setQuantity(savedOrder.getQuantity());

        return dto;
    }


    // Get All
    public List<Order> getAll() {
        return orderRepository.findAll();
    }
    // Get By Id
    public Order getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }


//    // Update
//    public Order update(Long id, OrderRequestDto request) {
//
//        Order order = orderRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Order not found"));
//
//
//        order.setQuantity(request.getQuantity());
//        order.setOrderDate(request.getOrderDate());
//
//
//        return orderRepository.save(order);
//    }

    // Delete
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}