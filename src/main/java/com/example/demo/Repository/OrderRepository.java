package com.example.demo.Repository;

import com.example.demo.Entity.Order;
import com.example.demo.Entity.Product;
import com.example.demo.Entity.User;
import com.example.demo.Projection.OrderProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long >{
    Page<OrderProjection> findAllProjectedBy(Pageable pageable);

    List<Order> findByUserId(Long userId);
    List<Order> findByProductId(Long productId);

  List<Order> findByUser(User User);
  List<Order> findByCreatedAt(LocalDateTime createdAt);

    List<Order> findByUserAndProduct(User user, Product product);
//  List<Order> findByUserLike(String User);

}
