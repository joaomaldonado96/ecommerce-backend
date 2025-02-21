package com.example.ecommerce.services;
import com.example.ecommerce.entities.SaleProduct;
import com.example.ecommerce.entities.SaleProductPK;
import com.example.ecommerce.repositories.SaleProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
class SaleProductService {
    private final SaleProductRepository saleProductRepository;

    public SaleProductService(SaleProductRepository saleProductRepository) {
        this.saleProductRepository = saleProductRepository;
    }

    public List<SaleProduct> getSaleProductsBySaleId(Long saleId) {
        return saleProductRepository.findAll();
    }

    @Transactional
    public SaleProduct saveSaleProduct(SaleProduct saleProduct) {
        return saleProductRepository.save(saleProduct);
    }

    @Transactional
    public void deleteSaleProduct(SaleProductPK id) {
        saleProductRepository.deleteById(id);
    }
}