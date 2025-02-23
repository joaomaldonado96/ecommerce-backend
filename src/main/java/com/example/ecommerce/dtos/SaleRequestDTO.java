package com.example.ecommerce.dtos;
import com.example.ecommerce.entities.Sale;
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
public class SaleRequestDTO {
    private String personEmail;
    private BigDecimal discount;

    public Sale toEntity() {
        Sale sale = new Sale();
        sale.setDiscount(this.discount);
        sale.setCreatedAt(Instant.now());
        return sale;
    }
}