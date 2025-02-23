package com.example.ecommerce.controllers;


import com.example.ecommerce.dtos.SaleDTO;
import com.example.ecommerce.services.SaleService;
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
class SaleControllerTest {

    @Mock
    private SaleService saleService;

    @InjectMocks
    private SaleController saleController;

    private SaleDTO saleDTO;

    @BeforeEach
    void setUp() {
        saleDTO = new SaleDTO();
        saleDTO.setId(1L);
        saleDTO.setCreatedAt(Instant.now());
        saleDTO.setDiscount(BigDecimal.valueOf(10.0));
        saleDTO.setPersonEmail("test@example.com");
    }

    @Test
    void testGetAllSales() {
        when(saleService.getAllSales()).thenReturn(List.of(saleDTO));

        ResponseEntity<List<SaleDTO>> response = saleController.getAllSales();

        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
        assertEquals(saleDTO, response.getBody().get(0));

        verify(saleService, times(1)).getAllSales();
    }

    @Test
    void testGetSaleById_Found() {
        when(saleService.getSaleById(1L)).thenReturn(Optional.of(saleDTO));

        ResponseEntity<SaleDTO> response = saleController.getSaleById(1L);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(saleDTO, response.getBody());
    }

    @Test
    void testGetSaleById_NotFound() {
        when(saleService.getSaleById(2L)).thenReturn(Optional.empty());

        ResponseEntity<SaleDTO> response = saleController.getSaleById(2L);

        assertEquals(404, response.getStatusCode().value());
        assertNull(response.getBody());
    }

    @Test
    void testGetSalesByPersonEmail() {
        when(saleService.getSalesByPersonEmail("test@example.com")).thenReturn(List.of(saleDTO));

        ResponseEntity<List<SaleDTO>> response = saleController.getSalesByPersonEmail("test@example.com");

        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
        assertEquals("test@example.com", response.getBody().get(0).getPersonEmail());

        verify(saleService, times(1)).getSalesByPersonEmail("test@example.com");
    }

    @Test
    void testFindTop5FrequentCustomers() {
        List<Object[]> topCustomers = List.of(
                new Object[]{"test@example.com", "Juan", 2.5},
                new Object[]{"customer2@example.com", "Customer Two", 3.7},
                new Object[]{"customer3@example.com", "Customer Three", 4.1},
                new Object[]{"customer4@example.com", "Customer Four", 5.2},
                new Object[]{"customer5@example.com", "Customer Five", 6.0}
        );
        when(saleService.findTop5FrequentCustomers()).thenReturn(topCustomers);

        ResponseEntity<List<Object[]>> response = saleController.findTop5FrequentCustomers();

        assertNotNull(response.getBody());
        assertEquals(5, response.getBody().size());
        assertArrayEquals(topCustomers.get(0), response.getBody().get(0));

        verify(saleService, times(1)).findTop5FrequentCustomers();
    }

    @Test
    void testCreateSale() {
        when(saleService.saveSale(saleDTO)).thenReturn(saleDTO);

        ResponseEntity<SaleDTO> response = saleController.createSale(saleDTO);

        assertNotNull(response.getBody());
        assertEquals(saleDTO, response.getBody());
        assertEquals(201, response.getStatusCode().value());

        verify(saleService, times(1)).saveSale(saleDTO);
    }

    @Test
    void testDeleteSale() {
        doNothing().when(saleService).deleteSale(1L);

        ResponseEntity<Void> response = saleController.deleteSale(1L);

        assertEquals(204, response.getStatusCode().value());
        verify(saleService, times(1)).deleteSale(1L);
    }
}