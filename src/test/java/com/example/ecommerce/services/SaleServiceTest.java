package com.example.ecommerce.services;

import com.example.ecommerce.entities.Person;
import com.example.ecommerce.entities.Sale;
import com.example.ecommerce.repositories.PersonRepository;
import com.example.ecommerce.repositories.SaleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class SaleServiceTest {

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private SaleService saleService;

    private Sale sale;
    private Person person;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        person = new Person();
        person.setEmail("test@example.com");

        sale = new Sale();
        sale.setDiscount(BigDecimal.valueOf(10));
        sale.setCreatedAt(Instant.now());
        sale.setPerson(person);
    }

    @Test
    public void testGetAllSales() {

        Sale sale1 = new Sale();
        sale1.setDiscount(BigDecimal.valueOf(5));
        sale1.setCreatedAt(Instant.now());

        Sale sale2 = new Sale();
        sale2.setDiscount(BigDecimal.valueOf(15));
        sale2.setCreatedAt(Instant.now());

        when(saleRepository.findAll()).thenReturn(Arrays.asList(sale1, sale2));
        List<Sale> sales = saleService.getAllSales();
        assertNotNull(sales);
        assertEquals(2, sales.size());
    }

    @Test
    public void testGetSaleById() {
        when(saleRepository.findById(1L)).thenReturn(Optional.of(sale));
        Optional<Sale> foundSale = saleService.getSaleById(1L);
        assertTrue(foundSale.isPresent());
        assertEquals(sale.getDiscount(), foundSale.get().getDiscount());
    }

    @Test
    public void testGetSalesByPersonEmail() {
        when(saleRepository.findByPersonEmail(person.getEmail())).thenReturn(Arrays.asList(sale));
        List<Sale> sales = saleService.getSalesByPersonEmail(person.getEmail());
        assertNotNull(sales);
        assertEquals(1, sales.size());
        assertEquals(person.getEmail(), sales.get(0).getPerson().getEmail());
    }

    @Test
    public void testFindTop5FrequentCustomers() {
        Object[] customer1 = { "test1@example.com", "Customer 1", 5.5 }; // Cliente 1, con un promedio de 5.5 días entre compras
        Object[] customer2 = { "test2@example.com", "Customer 2", 6.0 }; // Cliente 2, con un promedio de 6.0 días
        Object[] customer3 = { "test3@example.com", "Customer 3", 7.2 }; // Cliente 3, con un promedio de 7.2 días
        Object[] customer4 = { "test4@example.com", "Customer 4", 8.0 }; // Cliente 4, con un promedio de 8.0 días
        Object[] customer5 = { "test5@example.com", "Customer 5", 9.1 }; // Cliente 5, con un promedio de 9.1 días

        when(saleRepository.findTop5FrequentCustomers())
                .thenReturn(Arrays.asList(customer1, customer2, customer3, customer4, customer5));

        List<Object[]> topCustomers = saleService.findTop5FrequentCustomers();

        assertNotNull(topCustomers);
        assertEquals(5, topCustomers.size());

        assertArrayEquals(customer1, topCustomers.get(0));
        assertArrayEquals(customer2, topCustomers.get(1));
        assertArrayEquals(customer3, topCustomers.get(2));
        assertArrayEquals(customer4, topCustomers.get(3));
        assertArrayEquals(customer5, topCustomers.get(4));
    }



    @Test
    public void testSaveSale_Success() {
        when(personRepository.findById(person.getEmail())).thenReturn(Optional.of(person));
        when(saleRepository.save(sale)).thenReturn(sale);
        Sale savedSale = saleService.saveSale(sale, person.getEmail());
        assertNotNull(savedSale);
        assertEquals(sale.getDiscount(), savedSale.getDiscount());
        assertNotNull(savedSale.getCreatedAt());
    }

    @Test
    public void testSaveSale_PersonNotFound() {
        when(personRepository.existsByEmail(person.getEmail())).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> saleService.saveSale(sale,"test1@example.com"));
    }

    @Test
    public void testDeleteSale() {
        doNothing().when(saleRepository).deleteById(1L);
        saleService.deleteSale(1L);
        verify(saleRepository, times(1)).deleteById(1L);
    }
}