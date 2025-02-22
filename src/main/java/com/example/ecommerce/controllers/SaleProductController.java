package com.example.ecommerce.controllers;

import com.example.ecommerce.entities.Product;
import com.example.ecommerce.entities.SaleProduct;
import com.example.ecommerce.entities.SaleProductPK;
import com.example.ecommerce.services.SaleProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sale-products")
class SaleProductController {
    private final SaleProductService saleProductService;

    public SaleProductController(SaleProductService saleProductService) {
        this.saleProductService = saleProductService;
    }

    @GetMapping
    public List<SaleProduct> getAllSaleProducts() {
        return saleProductService.getSaleProductsBySaleId(null);
    }

    @GetMapping("/top-products")
    public List<Product> getTop5BestSellingProducts() {
        return saleProductService.getTop5BestSellingProducts();
    }

    @PostMapping
    public SaleProduct createSaleProduct(@RequestBody SaleProduct saleProduct) {
        return saleProductService.saveSaleProduct(saleProduct);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteSaleProduct(@RequestBody SaleProductPK id) {
        saleProductService.deleteSaleProduct(id);
        return ResponseEntity.noContent().build();
    }
}