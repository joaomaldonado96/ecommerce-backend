package com.example.ecommerce.controllers;

import com.example.ecommerce.entities.Product;
import com.example.ecommerce.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("");
        product.setDescription("A sample product for testing");
        product.setPrice(BigDecimal.valueOf(100.0));
        product.setStock(10);
        product.setCreatedAt(Instant.now());
        product.setUpdatedAt(Instant.now());
    }

    @Test
    void testGetAllProducts() {
        when(productService.getAllProducts()).thenReturn(List.of(product));

        List<Product> products = productController.getAllProducts();

        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void testGetActiveProducts() {
        when(productService.getActiveProducts()).thenReturn(List.of(product));

        List<Product> products = productController.getActiveProducts();

        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
        verify(productService, times(1)).getActiveProducts();
    }

    @Test
    void testGetProductById_Found() {
        when(productService.getProductById(1L)).thenReturn(Optional.of(product));

        ResponseEntity<Product> response = productController.getProductById(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(product, response.getBody());
    }

    @Test
    void testGetProductById_NotFound() {
        when(productService.getProductById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Product> response = productController.getProductById(1L);

        assertEquals(404, response.getStatusCode().value());
        assertNull(response.getBody());
    }

    @Test
    void testCreateProduct_Success() {
        when(productService.saveProduct(product)).thenReturn(product);

        ResponseEntity<Product> response = productController.createProduct(product);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(product, response.getBody());
    }

    @Test
    void testUpdateProduct_Success() {
        when(productService.updateProduct(eq(1L), any(Product.class), anyString())).thenReturn(product);

        ResponseEntity<Product> response = productController.updateProduct(1L, product, "admin@example.com");

        assertEquals(200, response.getStatusCode().value());
        assertEquals(product, response.getBody());
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(productService).deleteProduct(1L);

        ResponseEntity<Void> response = productController.deleteProduct(1L);

        assertEquals(204, response.getStatusCode().value());
        verify(productService, times(1)).deleteProduct(1L);
    }
}
