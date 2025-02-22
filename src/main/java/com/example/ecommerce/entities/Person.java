package com.example.ecommerce.entities;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;


@Entity
@Table(name = "person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    private String email;
    private String password;
    private String name;
    private String address;
    private String phone;
    private Boolean isFrequentCustomer;
    private Instant createdAt;

}