package com.example.ecommerce.controllers;

import com.example.ecommerce.dtos.ProductRequestDTO;
import com.example.ecommerce.dtos.ProductResponseDTO;
import com.example.ecommerce.entities.Product;
import com.example.ecommerce.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
class ProductController{
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponseDTO> getAllProducts() {
        return productService.getAllProducts().stream()
                .map(ProductResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/active")
    public List<ProductResponseDTO> getActiveProducts() {
        return productService.getActiveProducts().stream()
                .map(ProductResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(product -> ResponseEntity.ok(ProductResponseDTO.fromEntity(product)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        Product product = productService.saveProduct(productRequestDTO.toEntity(), productRequestDTO.getUpdatedByEmail());
        return ResponseEntity.ok(ProductResponseDTO.fromEntity(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO productRequestDTO) {
        Product updatedProduct = productService.updateProduct(id, productRequestDTO.toEntity(), productRequestDTO.getUpdatedByEmail());
        return ResponseEntity.ok(ProductResponseDTO.fromEntity(updatedProduct));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
