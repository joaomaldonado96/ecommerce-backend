package com.example.ecommerce.entities;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.Set;


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

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Sale> sales;

    @OneToMany(mappedBy = "updatedByPerson", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Product> updatedProducts;
}