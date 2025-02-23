package com.example.ecommerce.dtos;
import com.example.ecommerce.entities.SaleProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaleProductRequestDTO {
    private Long sale_id;
    private Long product_id;
    private Integer quantity;
    private BigDecimal unitPrice;

    public SaleProduct toEntity() {
        SaleProduct saleProduct = new SaleProduct();
        saleProduct.setQuantity(this.quantity);
        saleProduct.setUnitPrice(this.unitPrice);
        return saleProduct;
    }
}
