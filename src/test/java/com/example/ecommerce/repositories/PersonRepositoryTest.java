package com.example.ecommerce.repositories;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.ecommerce.entities.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PersonRepositoryTest {

    @Mock
    private PersonRepository personRepository;

    @Test
    void testFindByEmailAndPassword() {
        Person person = new Person();
        person.setEmail("test@example.com");
        person.setPassword("securepassword");
        person.setName("Juan");

        when(personRepository.findByEmailAndPassword("test@example.com", "securepassword"))
                .thenReturn(Optional.of(person));

        Optional<Person> foundPerson = personRepository.findByEmailAndPassword("test@example.com", "securepassword");

        assertTrue(foundPerson.isPresent());
        assertEquals("Juan", foundPerson.get().getName());
    }

    @Test
    void testExistsByEmail() {

        when(personRepository.existsByEmail("test@example.com")).thenReturn(true);

        assertTrue(personRepository.existsByEmail("test@example.com"));
    }
}