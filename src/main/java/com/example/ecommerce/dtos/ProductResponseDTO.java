package com.example.ecommerce.dtos;
import com.example.ecommerce.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private Boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;
    private String updatedByEmail;

    public static ProductResponseDTO fromEntity(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getIsActive(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                product.getUpdatedByPerson() != null ? product.getUpdatedByPerson().getEmail() : null
        );
    }
}
