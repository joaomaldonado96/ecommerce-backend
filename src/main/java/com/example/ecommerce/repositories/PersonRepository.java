package com.example.ecommerce.repositories;

import com.example.ecommerce.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, String> {
    boolean existsByEmail(String email);
}