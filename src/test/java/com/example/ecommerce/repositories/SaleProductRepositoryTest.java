package com.example.ecommerce.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.example.ecommerce.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class SaleProductRepositoryTest {

    @Mock
    private SaleProductRepository saleProductRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindTop5BestSellingProducts() {
        Product product1 = new Product();
        product1.setName("Producto 1");
        Product product2 = new Product();
        product2.setName("Producto 2");

        List<Product> mockProducts = Arrays.asList(product1, product2);

        when(saleProductRepository.findTop5BestSellingProducts()).thenReturn(mockProducts);

        List<Product> result = saleProductRepository.findTop5BestSellingProducts();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getName()).isEqualTo("Producto 1");
        assertThat(result.get(1).getName()).isEqualTo("Producto 2");

        verify(saleProductRepository, times(1)).findTop5BestSellingProducts();
    }
}