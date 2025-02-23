package com.example.ecommerce.dtos;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaleDTO {
    private Long id;
    private String personEmail;
    private Instant createdAt;
    private BigDecimal discount;
}
