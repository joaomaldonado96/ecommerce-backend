package com.example.ecommerce.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.*;

import com.example.ecommerce.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class SaleProductRepositoryTest {

    @Mock
    private SaleProductRepository saleProductRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindTop5BestSellingProducts() {

        Object[] product1 = {"33","Router TP-Link Archer AX50","Router WiFi 6 TP-Link Archer AX50 AX3000",
                "83.00",45,true,"2025-02-22 23:42:02.446788+00","2025-02-22 23:42:02.446788+00","user3@example.com","10"};
        Object[] product2 = {"38","Disco duro externo WD 2TB","Disco duro externo Western Digital 2TB USB 3.0",
                "92.00",50,true,"2025-02-22 23:42:02.446788+00","2025-02-22 23:42:02.446788+00","user8@example.com","8"};
        Object[] product3 = {"28","Placa base MSI B550","Placa base MSI B550 Tomahawk ATX","211.00",25,true,
                "2025-02-22 23:42:02.446788+00","2025-02-22 23:42:02.446788+00","user8@example.com","8"};
        Object[] product4 = {"30","Silla gamer DXRacer","Silla ergonómica DXRacer Racing Series","316.00",
                15,true,"2025-02-22 23:42:02.446788+00","2025-02-22 23:42:02.446788+00","user10@example.com","7"};
        Object[] product5 = {"35","Smartwatch Apple Watch SE","Smartwatch Apple Watch SE GPS 40mm","202.00",
                35,true,"2025-02-22 23:42:02.446788+00","2025-02-22 23:42:02.446788+00","user5@example.com","7"};

        when(saleProductRepository.findTop5BestSellingProducts())
                .thenReturn(Arrays.asList(product1, product2, product3, product4, product5));

        List<Object[]> result = saleProductRepository.findTop5BestSellingProducts();

        assertNotNull(result);
        assertEquals(5, result.size());

        assertArrayEquals(product1, result.get(0));
        assertArrayEquals(product2, result.get(1));
        assertArrayEquals(product3, result.get(2));
        assertArrayEquals(product4, result.get(3));
        assertArrayEquals(product5, result.get(4));

        verify(saleProductRepository, times(1)).findTop5BestSellingProducts();
    }
}