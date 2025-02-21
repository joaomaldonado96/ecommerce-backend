package com.example.ecommerce.entities;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SaleProductPK implements Serializable {
    private Long saleId;
    private Long productId;
}
