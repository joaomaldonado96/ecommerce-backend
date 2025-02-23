package com.example.ecommerce.dtos;
import com.example.ecommerce.entities.Sale;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class SaleResponseDTO {
    private Long id;
    private String personEmail;
    private Instant createdAt;
    private BigDecimal discount;

    public static SaleResponseDTO fromEntity(Sale sale) {
        return new SaleResponseDTO(
                sale.getId(),
                sale.getPerson().getEmail(),
                sale.getCreatedAt(),
                sale.getDiscount()
        );
    }

    public SaleResponseDTO(Long id, String personEmail, Instant createdAt, BigDecimal discount) {
        this.id = id;
        this.personEmail = personEmail;
        this.createdAt = createdAt;
        this.discount = discount;
    }

}