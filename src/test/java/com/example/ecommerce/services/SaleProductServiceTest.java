package com.example.ecommerce.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.ecommerce.entities.Product;
import com.example.ecommerce.entities.SaleProduct;
import com.example.ecommerce.entities.SaleProductPK;
import com.example.ecommerce.repositories.SaleProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

public class SaleProductServiceTest {

    @Mock
    private SaleProductRepository saleProductRepository;

    @InjectMocks
    private SaleProductService saleProductService;

    private SaleProduct saleProduct;
    private SaleProductPK saleProductPK;
    private List<SaleProduct> saleProducts;
    private List<Product> topProducts;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        saleProductPK = new SaleProductPK(1L, 1L);
        saleProduct = new SaleProduct();
        saleProduct.setId(saleProductPK);

        saleProducts = Arrays.asList(saleProduct, new SaleProduct());
        topProducts = Arrays.asList(new Product(), new Product());
    }

    @Test
    public void testGetSaleProductsBySaleId() {
        when(saleProductRepository.findAll()).thenReturn(saleProducts);

        List<SaleProduct> result = saleProductService.getSaleProductsBySaleId(1L);

        assertEquals(saleProducts.size(), result.size());
        verify(saleProductRepository).findAll();
    }

    @Test
    public void testSaveSaleProduct() {
        when(saleProductRepository.save(saleProduct)).thenReturn(saleProduct);

        SaleProduct result = saleProductService.saveSaleProduct(saleProduct);

        assertEquals(saleProduct, result);
        verify(saleProductRepository).save(saleProduct);
    }

    @Test
    public void testGetTop5BestSellingProducts() {
        when(saleProductRepository.findTop5BestSellingProducts()).thenReturn(topProducts);

        List<Product> result = saleProductService.getTop5BestSellingProducts();

        assertEquals(topProducts.size(), result.size());
        verify(saleProductRepository).findTop5BestSellingProducts();
    }

    @Test
    public void testDeleteSaleProduct() {
        doNothing().when(saleProductRepository).deleteById(saleProductPK);

        saleProductService.deleteSaleProduct(saleProductPK);

        verify(saleProductRepository).deleteById(saleProductPK);
    }
}