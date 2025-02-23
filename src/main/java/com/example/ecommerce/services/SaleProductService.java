package com.example.ecommerce.services;
import com.example.ecommerce.entities.*;
import com.example.ecommerce.repositories.PersonRepository;
import com.example.ecommerce.repositories.ProductRepository;
import com.example.ecommerce.repositories.SaleProductRepository;
import com.example.ecommerce.repositories.SaleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class SaleProductService {
    private final SaleProductRepository saleProductRepository;
    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;

    public SaleProductService(SaleProductRepository saleProductRepository,
                              SaleRepository saleRepository, ProductRepository productRepository) {
        this.saleProductRepository = saleProductRepository;
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
    }

    public List<SaleProduct> getAllSaleProducts() {
        return saleProductRepository.findAll();
    }


    public List<SaleProduct>  getSaleProductsBySaleId(Long saleId) {
        return saleProductRepository.getSaleProductsBySaleId(saleId);
    }

    @Transactional
    public SaleProduct saveSaleProduct(SaleProduct saleProduct, Long saleId, Long productId ) {
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la venta con el id proporcionado"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el producto con el id proporcionado"));
        saleProduct.setId(new SaleProductPK(saleId, productId));
        saleProduct.setSale(sale);
        saleProduct.setProduct(product);

        return saleProductRepository.save(saleProduct);
    }

    public List<Object[]> getTop5BestSellingProducts() {
        return saleProductRepository.findTop5BestSellingProducts();
    }

    @Transactional
    public void deleteSaleProduct(SaleProductPK id) {
        saleProductRepository.deleteById(id);
    }
}