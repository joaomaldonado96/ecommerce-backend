package com.example.ecommerce.repositories;
import com.example.ecommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ecommerce.entities.SaleProduct;
import com.example.ecommerce.entities.SaleProductPK;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleProductRepository extends JpaRepository<SaleProduct, SaleProductPK> {

    @Query("SELECT sp.product FROM SaleProduct sp GROUP BY sp.product ORDER BY SUM(sp.quantity) DESC LIMIT 5")
    List<Product> findTop5BestSellingProducts();
}