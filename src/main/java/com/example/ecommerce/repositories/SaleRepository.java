package com.example.ecommerce.repositories;
import com.example.ecommerce.constants.QueryConstants;
import com.example.ecommerce.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query(value = QueryConstants.TOP_5_FREQUENT_CUSTOMERS, nativeQuery = true)
    List<Object[]> findTop5FrequentCustomers();
}
