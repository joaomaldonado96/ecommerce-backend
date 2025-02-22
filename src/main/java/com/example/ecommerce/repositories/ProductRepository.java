package com.example.ecommerce.repositories;
import com.example.ecommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStockGreaterThan(Integer stock);
}