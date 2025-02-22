package com.example.ecommerce.services;

import com.example.ecommerce.entities.Product;
import com.example.ecommerce.entities.Person;
import com.example.ecommerce.repositories.ProductRepository;
import com.example.ecommerce.repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PersonRepository personRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductService(productRepository, personRepository);
    }

    @Test
    void testGetAllProducts() {
        when(productRepository.findAll()).thenReturn(List.of(new Product()));

        var products = productService.getAllProducts();

        assertNotNull(products);
        assertFalse(products.isEmpty());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductById() {
        Product product = new Product();
        product.setId(1L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Optional<Product> foundProduct = productService.getProductById(1L);

        assertTrue(foundProduct.isPresent());
        assertEquals(1L, foundProduct.get().getId());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveProductWithInvalidPrice() {
        Product product = new Product();
        product.setPrice(BigDecimal.ZERO);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            productService.saveProduct(product);
        });

        assertEquals("El precio debe ser mayor a 0", thrown.getMessage());
        verify(productRepository, times(0)).save(any(Product.class));
    }

    @Test
    void testSaveProductWithInvalidStock() {
        Product product = new Product();
        product.setPrice(BigDecimal.TEN);
        product.setStock(-1);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            productService.saveProduct(product);
        });

        assertEquals("El stock no puede ser negativo", thrown.getMessage());
        verify(productRepository, times(0)).save(any(Product.class));
    }

    @Test
    void testSaveProductSuccessfully() {
        Product product = new Product();
        product.setPrice(BigDecimal.TEN);
        product.setStock(10);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product savedProduct = productService.saveProduct(product);

        assertNotNull(savedProduct);
        assertEquals(BigDecimal.TEN, savedProduct.getPrice());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testUpdateProduct() {
        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setPrice(BigDecimal.TEN);
        existingProduct.setStock(10);

        Product updatedProduct = new Product();
        updatedProduct.setName("Updated Name");
        updatedProduct.setDescription("Updated Description");
        updatedProduct.setPrice(BigDecimal.valueOf(20));
        updatedProduct.setStock(20);

        Person updatedByPerson = new Person();
        updatedByPerson.setEmail("test@example.com");

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);
        when(personRepository.findById("test@example.com")).thenReturn(Optional.of(updatedByPerson));

        Product updatedProductResult = productService.updateProduct(1L, updatedProduct, "test@example.com");

        assertNotNull(updatedProductResult);
        assertEquals("Updated Name", updatedProductResult.getName());
        assertEquals(BigDecimal.valueOf(20), updatedProductResult.getPrice());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(productRepository).deleteById(1L);

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }
}