package com.example.ecommerce.controllers;

import com.example.ecommerce.dtos.SaleDTO;
import com.example.ecommerce.entities.Sale;
import com.example.ecommerce.services.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
class SaleController {
    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping
    public ResponseEntity<List<SaleDTO>> getAllSales() {
        return ResponseEntity.ok(saleService.getAllSales());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleDTO> getSaleById(@PathVariable Long id) {
        return saleService.getSaleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/person/{email}")
    public ResponseEntity<List<SaleDTO>> getSalesByPersonEmail(@PathVariable String email) {
        List<SaleDTO> sales = saleService.getSalesByPersonEmail(email);
        return ResponseEntity.ok(sales);
    }

    @GetMapping("/top-frequent-customers")
    public ResponseEntity<List<Object[]>> findTop5FrequentCustomers() {
        return ResponseEntity.ok(saleService.findTop5FrequentCustomers());
    }

    @PostMapping
    public ResponseEntity<SaleDTO> createSale(@RequestBody SaleDTO saleDTO) {
        SaleDTO savedSale = saleService.saveSale(saleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSale);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        saleService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }
}
