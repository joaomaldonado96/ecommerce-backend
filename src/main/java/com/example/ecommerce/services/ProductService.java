package com.example.ecommerce.services;
import com.example.ecommerce.entities.Person;
import com.example.ecommerce.entities.Product;
import com.example.ecommerce.repositories.PersonRepository;
import com.example.ecommerce.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
class ProductService {
    private final ProductRepository productRepository;
    private final PersonRepository personRepository;

    public ProductService(ProductRepository productRepository, PersonRepository personRepository) {
        this.productRepository = productRepository;
        this.personRepository = personRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public Product saveProduct(Product product) {
        if (product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }
        if (product.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Long id, Product updatedProduct, String updatedByEmail) {
        return productRepository.findById(id).map(existingProduct -> {
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setStock(updatedProduct.getStock());
            existingProduct.setIsActive(updatedProduct.getIsActive());
            existingProduct.setUpdatedAt(Instant.now());

            Person updatedByPerson = personRepository.findById(updatedByEmail)
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
            existingProduct.setUpdatedByPerson(updatedByPerson);

            return productRepository.save(existingProduct);
        }).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}