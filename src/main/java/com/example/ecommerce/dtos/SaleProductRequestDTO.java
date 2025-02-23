package com.example.ecommerce.dtos;
import com.example.ecommerce.entities.SaleProduct;

import java.math.BigDecimal;

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
