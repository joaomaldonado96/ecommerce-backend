package com.example.ecommerce.repositories;

import com.example.ecommerce.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, String> {
    boolean existsByEmail(String email);
    Optional<Person> findByEmailAndPassword(String email, String password);
}