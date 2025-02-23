package com.example.ecommerce.dtos;

import com.example.ecommerce.entities.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonResponseDTO {
    private String email;
    private String name;
    private String address;
    private String phone;
    private Boolean isFrequentCustomer;
    private Instant createdAt;

    public static PersonResponseDTO fromEntity(Person person) {
        return new PersonResponseDTO(
                person.getEmail(),
                person.getName(),
                person.getAddress(),
                person.getPhone(),
                person.getIsFrequentCustomer(),
                person.getCreatedAt()
        );
    }
}
