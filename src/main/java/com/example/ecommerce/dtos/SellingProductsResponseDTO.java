package com.example.ecommerce.dtos;

import com.example.ecommerce.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SellingProductsResponseDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private Boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;
    private String updatedByEmail;
    private Integer total_sold;


    public static List<SellingProductsResponseDTO> mapToSellingProductsResponseDTO(List<Object[]> results) {
        return results.stream().map(obj ->
                new SellingProductsResponseDTO(
                        ((Number) obj[0]).longValue(),      // id
                        (String) obj[1],                    // name
                        (String) obj[2],                    // description
                        new BigDecimal(String.valueOf(obj[3])), // price
                        ((Number) obj[4]).intValue(),       // stock
                        (Boolean) obj[5],                   // isActive
                        Instant.parse(String.valueOf(obj[6])), // createdAt
                        Instant.parse(String.valueOf(obj[7])), // updatedAt
                        (String) obj[8],                    // updatedByEmail
                        ((Number) obj[9]).intValue()        // total_sold
                )
        ).toList();
    }
}
