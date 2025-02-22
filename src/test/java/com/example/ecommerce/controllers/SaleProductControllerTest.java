package com.example.ecommerce.controllers;

import com.example.ecommerce.entities.*;
import com.example.ecommerce.services.SaleProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaleProductControllerTest {

    @Mock
    private SaleProductService saleProductService;

    @InjectMocks
    private SaleProductController saleProductController;

    private SaleProduct saleProduct;
    private SaleProductPK saleProductPK;
    private Product product;

    @BeforeEach
    void setUp() {

        product = new Product();
        product.setId(1L);
        product.setName("Sample Product");
        product.setDescription("A sample product for testing");

        saleProductPK = new SaleProductPK(1L, 1L);
        saleProduct = new SaleProduct();
        saleProduct.setId(saleProductPK);
        saleProduct.setProduct(product);
    }

    @Test
    void testGetAllSaleProducts() {
        when(saleProductService.getSaleProductsBySaleId(null)).thenReturn(List.of(saleProduct));

        List<SaleProduct> saleProducts = saleProductController.getAllSaleProducts();

        assertFalse(saleProducts.isEmpty());
        assertEquals(1, saleProducts.size());
        verify(saleProductService, times(1)).getSaleProductsBySaleId(null);
    }

    @Test
    void testGetTop5BestSellingProducts() {
        when(saleProductService.getTop5BestSellingProducts()).thenReturn(List.of(product));

        List<Product> products = saleProductController.getTop5BestSellingProducts();

        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
        verify(saleProductService, times(1)).getTop5BestSellingProducts();
    }

    @Test
    void testCreateSaleProduct() {
        when(saleProductService.saveSaleProduct(saleProduct)).thenReturn(saleProduct);

        SaleProduct response = saleProductController.createSaleProduct(saleProduct);

        assertEquals(saleProduct, response);
        verify(saleProductService, times(1)).saveSaleProduct(saleProduct);
    }

    @Test
    void testDeleteSaleProduct() {
        doNothing().when(saleProductService).deleteSaleProduct(saleProductPK);

        ResponseEntity<Void> response = saleProductController.deleteSaleProduct(saleProductPK);

        assertEquals(204, response.getStatusCode().value());
        verify(saleProductService, times(1)).deleteSaleProduct(saleProductPK);
    }
}
