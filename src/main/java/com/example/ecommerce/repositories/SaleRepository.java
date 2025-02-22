package com.example.ecommerce.repositories;
import com.example.ecommerce.constants.SaleQueries;
import com.example.ecommerce.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query(SaleQueries.FIND_BY_PERSON_EMAIL)
    List<Sale> findByPersonEmail(@Param("email") String email);

    @Query(value = SaleQueries.TOP_5_FREQUENT_CUSTOMERS, nativeQuery = true)
    List<Object[]> findTop5FrequentCustomers();
}
