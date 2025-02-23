package com.example.ecommerce.controllers;

import com.example.ecommerce.dtos.SaleProductRequestDTO;
import com.example.ecommerce.dtos.SaleProductResponseDTO;
import com.example.ecommerce.dtos.SaleResponseDTO;
import com.example.ecommerce.dtos.SellingProductsResponseDTO;
import com.example.ecommerce.entities.*;
import com.example.ecommerce.services.SaleProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
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
    private SaleProductRequestDTO saleProductRequestDTO;

    @BeforeEach
    void setUp() {

        Product product = new Product();
        product.setId(1L);
        product.setName("Sample Product");
        product.setDescription("A sample product for testing");
        Sale sale = new Sale();
        product.setId(1L);

        saleProductPK = new SaleProductPK(1L, 1L);
        saleProduct = new SaleProduct();
        saleProduct.setId(saleProductPK);
        saleProduct.setProduct(product);
        saleProduct.setSale(sale);

        saleProductRequestDTO = new SaleProductRequestDTO(
                1L,1L, 5, new BigDecimal("30.00")
        );


    }

    @Test
    void testGetAllSaleProducts() {
        when(saleProductService.getAllSaleProducts()).thenReturn(List.of(saleProduct));

        List<SaleProductResponseDTO> saleProductResponseDTO = saleProductController.getAllSaleProducts();

        assertFalse(saleProductResponseDTO.isEmpty());
        assertEquals(1, saleProductResponseDTO.size());
        verify(saleProductService, times(1)).getAllSaleProducts();
    }

    @Test
    void testGetTop5BestSellingProducts() {

        List<Object[]> topSellingProducts = Arrays.asList(
                new Object[]{33,"Router TP-Link Archer AX50","Router WiFi 6 TP-Link Archer AX50 AX3000","83.00",45,true,"2025-02-22T23:42:02.446788Z","2025-02-22T23:42:02.446788Z","user3@example.com",10},
                new Object[]{38,"Disco duro externo WD 2TB","Disco duro externo Western Digital 2TB USB 3.0","92.00",50,true,"2025-02-22T23:42:02.446788Z","2025-02-22T23:42:02.446788Z","user8@example.com",8},
                new Object[]{28,"Placa base MSI B550","Placa base MSI B550 Tomahawk ATX","211.00",25,true,"2025-02-22T23:42:02.446788Z","2025-02-22T23:42:02.446788Z","user8@example.com",8},
                new Object[]{30,"Silla gamer DXRacer","Silla ergonómica DXRacer Racing Series","316.00",15,true,"2025-02-22T23:42:02.446788Z","2025-02-22T23:42:02.446788Z","user10@example.com",7},
                new Object[]{35,"Smartwatch Apple Watch SE","Smartwatch Apple Watch SE GPS 40mm","202.00",35,true,"2025-02-22T23:42:02.446788Z","2025-02-22T23:42:02.446788Z","user5@example.com",7}
        );
        when(saleProductService.getTop5BestSellingProducts()).thenReturn(topSellingProducts);
        List<SellingProductsResponseDTO> response = SellingProductsResponseDTO.mapToSellingProductsResponseDTO(
                topSellingProducts);

        List<SellingProductsResponseDTO> products = saleProductController.getTop5BestSellingProducts();

        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertEquals(5, products.size());
        assertEquals(response.get(0).getId(), products.get(0).getId());
        verify(saleProductService, times(1)).getTop5BestSellingProducts();
    }

    @Test
    void testCreateSaleProduct() {
        when(saleProductService.saveSaleProduct(any(SaleProduct.class),eq(1L),eq(1L) )).thenReturn(saleProduct);

        ResponseEntity<SaleProductResponseDTO> response = saleProductController.createSaleProduct(saleProductRequestDTO);

        assertNotNull(response.getBody());
        assertEquals(saleProduct.getId().getSaleId(), response.getBody().getSale_id());
        assertEquals(200, response.getStatusCode().value());
        verify(saleProductService, times(1)).saveSaleProduct(any(SaleProduct.class),
                eq(1L),eq(1L) );
    }

    @Test
    void testDeleteSaleProduct() {
        doNothing().when(saleProductService).deleteSaleProduct(saleProductPK);

        ResponseEntity<Void> response = saleProductController.deleteSaleProduct(saleProductPK);

        assertEquals(204, response.getStatusCode().value());
        verify(saleProductService, times(1)).deleteSaleProduct(saleProductPK);
    }
}
