package com.example.demo.Repository;

import com.example.demo.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByCategory(String category);
    List<Product> findByStockGreaterThan(Integer Stock);
    List<Product> findByNameContainingIgnoreCase(String name);
}
