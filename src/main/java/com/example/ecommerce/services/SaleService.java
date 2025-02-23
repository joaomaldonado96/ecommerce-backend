package com.example.ecommerce.services;
import com.example.ecommerce.entities.Person;
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

    public List<Sale> getSalesByPersonEmail(String email) {
        return saleRepository.findByPersonEmail(email);
    }

    public List<Object[]> findTop5FrequentCustomers() {
        return saleRepository.findTop5FrequentCustomers();
    }

    @Transactional
    public Sale saveSale(Sale sale, String personEmail) {
        Person updatedByPerson = personRepository.findById(personEmail)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la persona con el email proporcionado"));
        sale.setCreatedAt(Instant.now());
        sale.setPerson(updatedByPerson);
        return saleRepository.save(sale);
    }

    public void deleteSale(Long id) {
        saleRepository.deleteById(id);
    }
}