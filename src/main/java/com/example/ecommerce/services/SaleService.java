package com.example.ecommerce.services;
import com.example.ecommerce.entities.Sale;
import com.example.ecommerce.repositories.PersonRepository;
import com.example.ecommerce.repositories.SaleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class SaleService {
    private final SaleRepository saleRepository;
    private final PersonRepository personRepository;

    public SaleService(SaleRepository saleRepository, PersonRepository personRepository) {
        this.saleRepository = saleRepository;
        this.personRepository = personRepository;
    }

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public Optional<Sale> getSaleById(Long id) {
        return saleRepository.findById(id);
    }

    public List<Object[]> findTop5FrequentCustomers() {
        return saleRepository.findTop5FrequentCustomers();
    }

    @Transactional
    public Sale saveSale(Sale sale) {
        if (!personRepository.existsByEmail(sale.getPerson().getEmail())) {
            throw new IllegalArgumentException("Persona no encontrada");
        }
        sale.setCreatedAt(Instant.now());
        return saleRepository.save(sale);
    }

    public void deleteSale(Long id) {
        saleRepository.deleteById(id);
    }
}