package com.example.ecommerce.services;

import com.example.ecommerce.entities.Person;
import com.example.ecommerce.repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    private Person person;

    @BeforeEach
    void setUp() {
        person = new Person();
        person.setEmail("test@example.com");
        person.setPassword("securepassword");
        person.setName("Juan");
        person.setAddress("123 Test Street");
        person.setPhone("1234567890");
    }

    @Test
    void testGetAllPersons() {
        when(personRepository.findAll()).thenReturn(List.of(person));

        List<Person> persons = personService.getAllPersons();

        assertFalse(persons.isEmpty());
        assertEquals(1, persons.size());
        verify(personRepository, times(1)).findAll();
    }

    @Test
    void testGetPersonByEmail() {
        when(personRepository.findById("test@example.com")).thenReturn(Optional.of(person));

        Optional<Person> foundPerson = personService.getPersonByEmail("test@example.com");

        assertTrue(foundPerson.isPresent());
        assertEquals("Juan", foundPerson.get().getName());
        verify(personRepository, times(1)).findById("test@example.com");
    }

    @Test
    void testSavePerson_Success() {
        when(personRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(personRepository.save(person)).thenReturn(person);

        Person savedPerson = personService.savePerson(person);

        assertEquals("Juan", savedPerson.getName());
        verify(personRepository, times(1)).save(person);
    }

    @Test
    void testSavePerson_EmailExists() {
        when(personRepository.existsByEmail("test@example.com")).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            personService.savePerson(person);
        });

        assertEquals("El correo ya está registrado", exception.getMessage());
    }

    @Test
    void testUpdatePerson_Success() {
        Person updatedPerson = new Person();
        updatedPerson.setName("Carlos");
        updatedPerson.setAddress("456 New Street");
        updatedPerson.setPhone("0987654321");

        when(personRepository.findById("test@example.com")).thenReturn(Optional.of(person));
        when(personRepository.save(any(Person.class))).thenReturn(updatedPerson);

        Person result = personService.updatePerson("test@example.com", updatedPerson);

        assertEquals("Carlos", result.getName());
        assertEquals("456 New Street", result.getAddress());
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    void testUpdatePerson_NotFound() {
        when(personRepository.findById("test@example.com")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            personService.updatePerson("test@example.com", person);
        });

        assertEquals("Persona no encontrada", exception.getMessage());
    }

    @Test
    void testLogin_Success() {
        when(personRepository.findByEmailAndPassword("test@example.com", "securepassword"))
                .thenReturn(Optional.of(person));

        Optional<Person> loggedInPerson = personService.login("test@example.com", "securepassword");

        assertTrue(loggedInPerson.isPresent());
        assertEquals("Juan", loggedInPerson.get().getName());
    }

    @Test
    void testDeletePerson() {
        doNothing().when(personRepository).deleteById("test@example.com");

        personService.deletePerson("test@example.com");

        verify(personRepository, times(1)).deleteById("test@example.com");
    }
}
