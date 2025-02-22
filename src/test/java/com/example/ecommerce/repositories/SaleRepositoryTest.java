package com.example.ecommerce.repositories;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.ecommerce.entities.Person;
import com.example.ecommerce.entities.Sale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;

public class SaleRepositoryTest  {

    @Mock
    private SaleRepository saleRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa los mocks
    }

    @Test
    public void testFindByPersonEmail() {
        Person person = new Person();
        person.setEmail("test@example.com");
        person.setPassword("securepassword");
        person.setName("Juan");

        String email = "test@example.com";
        Sale sale1 = new Sale();
        sale1.setId(1L);
        sale1.setPerson(person);

        Sale sale2 = new Sale();
        sale2.setId(2L);
        sale2.setPerson(person);

        List<Sale> sales = Arrays.asList(sale1, sale2);

        when(saleRepository.findByPersonEmail(email)).thenReturn(sales);

        List<Sale> result = saleRepository.findByPersonEmail(email);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(email, result.get(0).getPerson().getEmail());
        assertEquals(email, result.get(1).getPerson().getEmail());
    }

    @Test
    public void testFindTop5FrequentCustomers() {
        Object[] customer1 = { "test1@example.com", "Customer 1", 5.5 }; // Cliente 1
        Object[] customer2 = { "test2@example.com", "Customer 2", 6.0 }; // Cliente 2
        Object[] customer3 = { "test3@example.com", "Customer 3", 7.2 }; // Cliente 3
        Object[] customer4 = { "test4@example.com", "Customer 4", 8.0 }; // Cliente 4
        Object[] customer5 = { "test5@example.com", "Customer 5", 9.1 }; // Cliente 5

        when(saleRepository.findTop5FrequentCustomers())
                .thenReturn(Arrays.asList(customer1, customer2, customer3, customer4, customer5));

        List<Object[]> topCustomers = saleRepository.findTop5FrequentCustomers();

        assertNotNull(topCustomers);
        assertEquals(5, topCustomers.size());

        assertArrayEquals(customer1, topCustomers.get(0));
        assertArrayEquals(customer2, topCustomers.get(1));
        assertArrayEquals(customer3, topCustomers.get(2));
        assertArrayEquals(customer4, topCustomers.get(3));
        assertArrayEquals(customer5, topCustomers.get(4));
    }
}