package com.example.ecommerce.repositories;
import com.example.ecommerce.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
