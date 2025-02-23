package com.example.ecommerce.controllers;

import com.example.ecommerce.dtos.SaleProductRequestDTO;
import com.example.ecommerce.dtos.SaleProductResponseDTO;
import com.example.ecommerce.dtos.SaleResponseDTO;
import com.example.ecommerce.dtos.SellingProductsResponseDTO;
import com.example.ecommerce.entities.Product;
import com.example.ecommerce.entities.SaleProduct;
import com.example.ecommerce.entities.SaleProductPK;
import com.example.ecommerce.services.SaleProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sale-products")
class SaleProductController {
    private final SaleProductService saleProductService;

    public SaleProductController(SaleProductService saleProductService) {
        this.saleProductService = saleProductService;
    }

    @GetMapping
    public List<SaleProductResponseDTO> getAllSaleProducts() {
        return saleProductService.getAllSaleProducts().stream()
                .map(SaleProductResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("sale/{id}")
    public List<SaleProductResponseDTO> getSaleProductsBySaleId(@PathVariable Long id) {
        return saleProductService.getSaleProductsBySaleId(id).stream()
                .map(SaleProductResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/top-products")
    public List<SellingProductsResponseDTO> getTop5BestSellingProducts() {
        List<Object[]> results = saleProductService.getTop5BestSellingProducts();

        return SellingProductsResponseDTO.mapToSellingProductsResponseDTO(results);
    }

    @PostMapping
    public ResponseEntity<SaleProductResponseDTO> createSaleProduct(@RequestBody SaleProductRequestDTO saleProductRequestDTO) {
        SaleProduct saleProduct = saleProductService.saveSaleProduct(saleProductRequestDTO.toEntity(),
                saleProductRequestDTO.getProduct_id(),saleProductRequestDTO.getSale_id());
        return ResponseEntity.ok(SaleProductResponseDTO.fromEntity(saleProduct));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteSaleProduct(@RequestBody SaleProductPK id) {
        saleProductService.deleteSaleProduct(id);
        return ResponseEntity.noContent().build();
    }
}