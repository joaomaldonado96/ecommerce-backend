package com.example.ecommerce.entities;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "sale_products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleProduct {
    @EmbeddedId
    private SaleProductPK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("saleId")
    @JoinColumn(name = "sale_id")
    @ToString.Exclude
    private Sale sale;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    @ToString.Exclude
    private Product product;

    private Integer quantity;
    private BigDecimal unitPrice;
}