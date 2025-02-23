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
                saleProduct.getId() != null ? saleProduct.getId().getSaleId() : null,
                saleProduct.getId()  != null ? saleProduct.getId().getProductId() : null ,
                saleProduct.getQuantity(),
                saleProduct.getUnitPrice()
        );
    }
}
