package com.example.ecommerce.controllers;

import com.example.ecommerce.dtos.ProductRequestDTO;
import com.example.ecommerce.dtos.ProductResponseDTO;
import com.example.ecommerce.dtos.SaleRequestDTO;
import com.example.ecommerce.dtos.SaleResponseDTO;
import com.example.ecommerce.entities.Person;
import com.example.ecommerce.entities.Product;
import com.example.ecommerce.entities.Sale;
import com.example.ecommerce.services.ProductService;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SaleControllerTest {

    @Mock
    private SaleService saleService;

    @InjectMocks
    private SaleController saleController;

    private SaleRequestDTO saleRequestDTO;
    private SaleResponseDTO saleResponseDTO;
    private Sale sale;

    @BeforeEach
    public void setUp() {
        saleRequestDTO = new SaleRequestDTO();
        saleRequestDTO.setPersonEmail("test@example.com");
        saleRequestDTO.setDiscount(BigDecimal.valueOf(10.0));

        Person person = new Person();
        person.setEmail("test@example.com");

        sale = new Sale();
        sale.setId(1L);
        sale.setCreatedAt(Instant.now());
        sale.setPerson(person);
        sale.setDiscount(BigDecimal.valueOf(10.0));

    }

    @Test
    public void testGetAllSales() {
        when(saleService.getAllSales()).thenReturn(List.of(sale));

        List<SaleResponseDTO> response = saleController.getAllSales();

        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
        verify(saleService, times(1)).getAllSales();
    }

    @Test
    public void testGetSaleById_Found() {
        when(saleService.getSaleById(1L)).thenReturn(Optional.of(sale));

        ResponseEntity<SaleResponseDTO> response = saleController.getSaleById(1L);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(sale.getId(), response.getBody().getId());
    }

    @Test
    public void testGetSaleById_NotFound() {
        when(saleService.getSaleById(2L)).thenReturn(Optional.empty());

        ResponseEntity<SaleResponseDTO> response = saleController.getSaleById(2L);

        assertEquals(404, response.getStatusCode().value());
        assertNull(response.getBody());
    }

    @Test
    public void testGetSalesByPersonEmail() {
        when(saleService.getSalesByPersonEmail("test@example.com")).thenReturn(List.of(sale));

        List<SaleResponseDTO> response = saleController.getSalesByPersonEmail("test@example.com");

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
        assertEquals(sale.getPerson().getEmail(), response.get(0).getPersonEmail());

        verify(saleService, times(1)).getSalesByPersonEmail("test@example.com");
    }

    @Test
    public void testFindTop5FrequentCustomers() {
        List<Object[]> topCustomers = Arrays.asList(
                new Object[]{"test@example.com", "Juan", 2.5},
                new Object[]{"customer2@example.com", "Customer Two", 3.7},
                new Object[]{"customer3@example.com", "Customer Three", 4.1},
                new Object[]{"customer4@example.com", "Customer Four", 5.2},
                new Object[]{"customer5@example.com", "Customer Five", 6.0}
        );
        when(saleService.findTop5FrequentCustomers()).thenReturn(topCustomers);

        List<Object[]> response = saleController.findTop5FrequentCustomers();

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(5, response.size());
        assertArrayEquals(topCustomers.get(0), response.get(0));

        verify(saleService, times(1)).findTop5FrequentCustomers();
    }

    @Test
    public void testCreateSale() {
        when(saleService.saveSale(any(Sale.class), eq("test@example.com"))).thenReturn(sale);

        ResponseEntity<SaleResponseDTO> response = saleController.createSale(saleRequestDTO);

        assertNotNull(response.getBody());
        assertEquals(sale.getId(), response.getBody().getId());
        assertEquals(200, response.getStatusCode().value());

        verify(saleService, times(1)).saveSale(any(Sale.class), eq("test@example.com"));
    }

    @Test
    public void testDeleteSale() {
        doNothing().when(saleService).deleteSale(1L);

        ResponseEntity<Void> response = saleController.deleteSale(1L);

        assertEquals(204, response.getStatusCode().value());
        verify(saleService, times(1)).deleteSale(1L);
    }
}