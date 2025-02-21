package com.example.ecommerce.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ecommerce.entities.SaleProduct;
import com.example.ecommerce.entities.SaleProductPK;

public interface SaleProductRepository extends JpaRepository<SaleProduct, SaleProductPK> {
}