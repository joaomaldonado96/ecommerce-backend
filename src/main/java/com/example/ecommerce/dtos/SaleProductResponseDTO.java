package com.example.ecommerce.dtos;
import com.example.ecommerce.entities.SaleProduct;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SaleProductResponseDTO {
    private Long sale_id;
    private Long product_id;
    private Integer quantity;
    private BigDecimal unitPrice;

    public static SaleProductResponseDTO fromEntity(SaleProduct saleProduct) {
        return new SaleProductResponseDTO(
                saleProduct.getSale() != null ? saleProduct.getSale().getId() : null,
                saleProduct.getProduct()  != null ? saleProduct.getProduct().getId() : null ,
                saleProduct.getQuantity(),
                saleProduct.getUnitPrice()
        );
    }
}
