package com.example.ecommerce.controllers;

import com.example.ecommerce.dtos.SaleRequestDTO;
import com.example.ecommerce.dtos.SaleResponseDTO;
import com.example.ecommerce.dtos.TopFrequentCustomersDTO;
import com.example.ecommerce.entities.Sale;
import com.example.ecommerce.repositories.PersonRepository;
import com.example.ecommerce.services.SaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sales")
class SaleController {
    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping
    public List<SaleResponseDTO> getAllSales() {
        return saleService.getAllSales().stream()
                .map(SaleResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleResponseDTO> getSaleById(@PathVariable Long id) {
        return saleService.getSaleById(id)
                .map(sale -> ResponseEntity.ok(SaleResponseDTO.fromEntity(sale)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/person/{email}")
    public List<SaleResponseDTO> getSalesByPersonEmail(@PathVariable String email) {
        return saleService.getSalesByPersonEmail(email).stream()
                .map(SaleResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/top-frequent-customers")
    public List<TopFrequentCustomersDTO> findTop5FrequentCustomers() {
        List<Object[]> results = saleService.findTop5FrequentCustomers();
        return TopFrequentCustomersDTO.mapToTopFrequentCustomersDTO(results);
    }

    @PostMapping
    public ResponseEntity<SaleResponseDTO> createSale(@RequestBody SaleRequestDTO saleRequestDTO) {
        Sale sale = saleService.saveSale(saleRequestDTO.toEntity(), saleRequestDTO.getPersonEmail());
        return ResponseEntity.ok(SaleResponseDTO.fromEntity(sale));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        saleService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }
}
