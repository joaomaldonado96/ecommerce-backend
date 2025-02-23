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
public class PersonRequestDTO {
    private String email;
    private String password;
    private String name;
    private String address;
    private String phone;
    private Boolean isFrequentCustomer;

    public Person ToEntity() {
        Person person = new Person();
        person.setEmail(this.email);
        person.setPassword(this.password);
        person.setName(this.name);
        person.setAddress(this.address);
        person.setPhone(this.phone);
        person.setIsFrequentCustomer(this.isFrequentCustomer);
        person.setCreatedAt(Instant.now());
        return person;
    }
}