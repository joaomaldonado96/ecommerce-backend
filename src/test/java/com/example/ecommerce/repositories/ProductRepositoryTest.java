package com.example.ecommerce.repositories;

import com.example.ecommerce.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProductRepositoryTest {

    @Mock
    private ProductRepository productRepository; // Simulamos el repositorio

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializamos los mocks
    }

    @Test
    public void testFindByStockGreaterThan() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Product 1");
        product1.setPrice(BigDecimal.valueOf(100));
        product1.setStock(10);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Product 2");
        product2.setPrice(BigDecimal.valueOf(50));
        product2.setStock(0);

        when(productRepository.findByStockGreaterThan(0)).thenReturn(Arrays.asList(product1));

        List<Product> activeProducts = productRepository.findByStockGreaterThan(0);

        assertNotNull(activeProducts);
        assertEquals(1, activeProducts.size());
        assertEquals("Product 1", activeProducts.get(0).getName());

        verify(productRepository, times(1)).findByStockGreaterThan(0);
    }
}