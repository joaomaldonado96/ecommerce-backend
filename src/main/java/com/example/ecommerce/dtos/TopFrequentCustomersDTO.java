package com.example.ecommerce.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TopFrequentCustomersDTO {
    private String email;
    private String name;
    private BigDecimal avgSales;

    public static List<TopFrequentCustomersDTO> mapToTopFrequentCustomersDTO(List<Object[]> results) {
        return results.stream().map(obj ->
                new TopFrequentCustomersDTO(
                        (String) obj[0],                    // email
                        (String) obj[1],                    // name
                        new BigDecimal(String.valueOf(obj[2])) // avgSales
                )
        ).toList();
    }
}
