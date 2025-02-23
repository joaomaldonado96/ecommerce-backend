package com.example.ecommerce.repositories;
import com.example.ecommerce.constants.SaleProductQueries;
import com.example.ecommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ecommerce.entities.SaleProduct;
import com.example.ecommerce.entities.SaleProductPK;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SaleProductRepository extends JpaRepository<SaleProduct, SaleProductPK> {
    @Query(SaleProductQueries.FIND_BY_SALE_ID)
    List<SaleProduct> getSaleProductsBySaleId(@Param("id") Long id);

    @Query(value = SaleProductQueries.TOP_5_BEST_SELLING_PRODUCTS, nativeQuery = true)
    List<Object[]> findTop5BestSellingProducts();
}