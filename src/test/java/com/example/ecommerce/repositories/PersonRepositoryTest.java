package com.example.ecommerce.repositories;

import com.example.ecommerce.entities.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @BeforeEach
    void setUp() {
        Person person = new Person();
        person.setEmail("test@example.com");
        person.setPassword("securepassword");
        person.setName("Juan");
        person.setAddress("123 Test Street");
        person.setPhone("1234567890");
        personRepository.save(person);
    }

    @Test
    void testFindByEmailAndPassword() {
        Optional<Person> foundPerson = personRepository.findByEmailAndPassword("test@example.com", "securepassword");
        assertTrue(foundPerson.isPresent());
        assertEquals("Juan", foundPerson.get().getName());
    }

    @Test
    void testExistsByEmail() {
        assertTrue(personRepository.existsByEmail("test@example.com"));
    }
}
