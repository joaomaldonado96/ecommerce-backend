package com.example.ecommerce.services;

import com.example.ecommerce.dtos.SaleDTO;
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
    private SaleDTO saleDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        person = new Person();
        person.setEmail("test@example.com");

        sale = new Sale();
        sale.setId(1L);
        sale.setDiscount(BigDecimal.valueOf(10));
        sale.setCreatedAt(Instant.now());
        sale.setPerson(person);

        saleDTO = new SaleDTO();
        saleDTO.setId(1L);
        saleDTO.setPersonEmail(person.getEmail());
        saleDTO.setCreatedAt(sale.getCreatedAt());
        saleDTO.setDiscount(sale.getDiscount());
    }

    @Test
    public void testGetAllSales() {
        when(saleRepository.findAll()).thenReturn(Arrays.asList(sale));

        List<SaleDTO> sales = saleService.getAllSales();

        assertNotNull(sales);
        assertEquals(1, sales.size());
        assertEquals(saleDTO.getDiscount(), sales.get(0).getDiscount());
        assertEquals(saleDTO.getPersonEmail(), sales.get(0).getPersonEmail());

        verify(saleRepository, times(1)).findAll();
    }

    @Test
    public void testGetSaleById() {
        when(saleRepository.findById(1L)).thenReturn(Optional.of(sale));

        Optional<SaleDTO> foundSale = saleService.getSaleById(1L);

        assertTrue(foundSale.isPresent());
        assertEquals(saleDTO.getDiscount(), foundSale.get().getDiscount());
        assertEquals(saleDTO.getPersonEmail(), foundSale.get().getPersonEmail());
    }

    @Test
    public void testGetSalesByPersonEmail() {
        when(saleRepository.findByPersonEmail(person.getEmail())).thenReturn(Arrays.asList(sale));

        List<SaleDTO> sales = saleService.getSalesByPersonEmail(person.getEmail());

        assertNotNull(sales);
        assertEquals(1, sales.size());
        assertEquals(person.getEmail(), sales.get(0).getPersonEmail());
    }

    @Test
    public void testFindTop5FrequentCustomers() {
        Object[] customer1 = { "test1@example.com", "Customer 1", 5.5 };
        Object[] customer2 = { "test2@example.com", "Customer 2", 6.0 };
        Object[] customer3 = { "test3@example.com", "Customer 3", 7.2 };
        Object[] customer4 = { "test4@example.com", "Customer 4", 8.0 };
        Object[] customer5 = { "test5@example.com", "Customer 5", 9.1 };

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
        when(saleRepository.save(any(Sale.class))).thenReturn(sale);

        SaleDTO savedSale = saleService.saveSale(saleDTO);

        assertNotNull(savedSale);
        assertEquals(saleDTO.getDiscount(), savedSale.getDiscount());
        assertEquals(saleDTO.getPersonEmail(), savedSale.getPersonEmail());

        verify(saleRepository, times(1)).save(any(Sale.class));
    }

    @Test
    public void testSaveSale_PersonNotFound() {
        when(personRepository.findById(person.getEmail())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> saleService.saveSale(saleDTO));
    }

    @Test
    public void testDeleteSale() {
        doNothing().when(saleRepository).deleteById(1L);
        saleService.deleteSale(1L);
        verify(saleRepository, times(1)).deleteById(1L);
    }
}