package com.example.ecommerce.services;
import com.example.ecommerce.dtos.SaleDTO;
import com.example.ecommerce.entities.Person;
import com.example.ecommerce.entities.Sale;
import com.example.ecommerce.repositories.PersonRepository;
import com.example.ecommerce.repositories.SaleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class SaleService {
    private final SaleRepository saleRepository;
    private final PersonRepository personRepository;

    public SaleService(SaleRepository saleRepository, PersonRepository personRepository) {
        this.saleRepository = saleRepository;
        this.personRepository = personRepository;
    }

    private SaleDTO convertToDTO(Sale sale) {
        return new SaleDTO(
                sale.getId(),
                sale.getPerson().getEmail(),
                sale.getCreatedAt(),
                sale.getDiscount()
        );
    }

    private Sale convertToEntity(SaleDTO saleDTO) {
        Person person = personRepository.findById(saleDTO.getPersonEmail())
                .orElseThrow(() -> new IllegalArgumentException("Persona no encontrada"));

        Sale sale = new Sale();
        sale.setId(saleDTO.getId());
        sale.setPerson(person);
        sale.setCreatedAt(saleDTO.getCreatedAt() != null ? saleDTO.getCreatedAt() : Instant.now());
        sale.setDiscount(saleDTO.getDiscount());

        return sale;
    }

    public List<SaleDTO> getAllSales() {
        return saleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<SaleDTO> getSaleById(Long id) {
        return saleRepository.findById(id).map(this::convertToDTO);
    }

    public List<SaleDTO> getSalesByPersonEmail(String email) {
        return saleRepository.findByPersonEmail(email).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<Object[]> findTop5FrequentCustomers() {
        return saleRepository.findTop5FrequentCustomers();
    }

    @Transactional
    public SaleDTO saveSale(SaleDTO saleDTO) {
        Sale sale = convertToEntity(saleDTO);
        Sale savedSale = saleRepository.save(sale);
        return convertToDTO(savedSale);
    }

    public void deleteSale(Long id) {
        saleRepository.deleteById(id);
    }
}