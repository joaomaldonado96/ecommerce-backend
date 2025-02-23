package com.example.ecommerce.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.ecommerce.entities.Product;
import com.example.ecommerce.entities.Sale;
import com.example.ecommerce.entities.SaleProduct;
import com.example.ecommerce.entities.SaleProductPK;
import com.example.ecommerce.repositories.ProductRepository;
import com.example.ecommerce.repositories.SaleProductRepository;
import com.example.ecommerce.repositories.SaleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

public class SaleProductServiceTest {

    @Mock
    private SaleProductRepository saleProductRepository;

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private SaleProductService saleProductService;

    private SaleProduct saleProduct;
    private SaleProductPK saleProductPK;
    private List<SaleProduct> saleProducts;
    private List<Product> topProducts;
    private Sale sale;
    private Product product;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        product = new Product();
        product.setId(1L);
        product.setName("Sample Product");
        product.setDescription("A sample product for testing");
        sale = new Sale();
        sale.setId(1L);

        saleProductPK = new SaleProductPK(1L, 1L);
        saleProduct = new SaleProduct();
        saleProduct.setId(saleProductPK);
        saleProduct.setProduct(product);
        saleProduct.setSale(sale);

        saleProducts = Arrays.asList(saleProduct, new SaleProduct());
        topProducts = Arrays.asList(new Product(), new Product());
    }

    @Test
    public void testGetSaleProductsBySaleId() {

        when(saleProductRepository.getSaleProductsBySaleId(1L)).thenReturn(Arrays.asList(saleProduct));
        List<SaleProduct> result = saleProductService.getSaleProductsBySaleId(1L);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(saleProduct.getId().getSaleId(), result.get(0).getId().getSaleId());
    }

    @Test
    public void testSaveSaleProduct() {
        when(saleRepository.findById(sale.getId())).thenReturn(Optional.of(sale));
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(saleProductRepository.save(saleProduct)).thenReturn(saleProduct);
        SaleProduct result = saleProductService.saveSaleProduct(saleProduct,1L, 1L);
        assertNotNull(result);
        assertEquals(saleProduct, result);
        verify(saleProductRepository).save(saleProduct);

    }

    @Test
    public void testGetTop5BestSellingProducts() {

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

        List<Object[]> result = saleProductService.getTop5BestSellingProducts();

        assertNotNull(result);
        assertEquals(5, result.size());

        assertArrayEquals(product1, result.get(0));
        assertArrayEquals(product2, result.get(1));
        assertArrayEquals(product3, result.get(2));
        assertArrayEquals(product4, result.get(3));
        assertArrayEquals(product5, result.get(4));
        verify(saleProductRepository, times(1)).findTop5BestSellingProducts();
    }

    @Test
    public void testDeleteSaleProduct() {
        doNothing().when(saleProductRepository).deleteById(saleProductPK);

        saleProductService.deleteSaleProduct(saleProductPK);

        verify(saleProductRepository).deleteById(saleProductPK);
    }
}