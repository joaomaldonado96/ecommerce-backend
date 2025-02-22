package com.example.ecommerce.controllers;


import com.example.ecommerce.entities.Person;
import com.example.ecommerce.entities.Sale;
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

    private Sale sale;
    private Person person;

    @BeforeEach
    void setUp() {
        person = new Person();
        person.setEmail("test@example.com");
        person.setPassword("securepassword");
        person.setName("Juan");
        person.setAddress("123 Test Street");
        person.setPhone("1234567890");
        person.setIsFrequentCustomer(false);
        person.setCreatedAt(Instant.now());

        sale = new Sale();
        sale.setId(1L);
        sale.setCreatedAt(Instant.now());
        sale.setDiscount(BigDecimal.valueOf(10.0));
        sale.setPerson(person);
    }

    @Test
    void testGetAllSales() {
        when(saleService.getAllSales()).thenReturn(List.of(sale));

        List<Sale> sales = saleController.getAllSales();

        assertFalse(sales.isEmpty());
        assertEquals(1, sales.size());
        verify(saleService, times(1)).getAllSales();
    }

    @Test
    void testGetSaleById_Found() {
        when(saleService.getSaleById(1L)).thenReturn(Optional.of(sale));

        ResponseEntity<Sale> response = saleController.getSaleById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(sale, response.getBody());
    }

    @Test
    void testGetSaleById_NotFound() {
        when(saleService.getSaleById(2L)).thenReturn(Optional.empty());

        ResponseEntity<Sale> response = saleController.getSaleById(2L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testGetSalesByPersonEmail() {
        when(saleService.getSalesByPersonEmail("test@example.com")).thenReturn(List.of(sale));

        List<Sale> sales = saleController.getSalesByPersonEmail("test@example.com");

        assertFalse(sales.isEmpty());
        assertEquals(1, sales.size());
        assertEquals("test@example.com", sales.get(0).getPerson().getEmail());
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

        List<Object[]> response = saleController.findTop5FrequentCustomers();

        assertEquals(5, response.size());
        assertEquals("test@example.com", response.get(0)[0]);
        assertEquals("Juan", response.get(0)[1]);
        assertEquals(2.5, (double) response.get(0)[2]);

        verify(saleService, times(1)).findTop5FrequentCustomers();
    }

    @Test
    void testCreateSale() {
        when(saleService.saveSale(sale)).thenReturn(sale);

        Sale response = saleController.createSale(sale);

        assertEquals(sale, response);
        verify(saleService, times(1)).saveSale(sale);
    }

    @Test
    void testDeleteSale() {
        doNothing().when(saleService).deleteSale(1L);

        ResponseEntity<Void> response = saleController.deleteSale(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(saleService, times(1)).deleteSale(1L);
    }
}