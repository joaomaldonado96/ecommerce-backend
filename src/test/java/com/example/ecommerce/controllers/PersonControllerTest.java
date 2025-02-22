package com.example.ecommerce.controllers;

import com.example.ecommerce.entities.Person;
import com.example.ecommerce.services.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonControllerTest {

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    private Person person;

    @BeforeEach
    void setUp() {
        person = new Person();
        person.setEmail("test@example.com");
        person.setPassword("securepassword");
        person.setName("Juan");
        person.setAddress("123 Test Street");
        person.setPhone("1234567890");
        person.setIsFrequentCustomer(false);
        person.setCreatedAt(Instant.now());
    }

    @Test
    void testGetAllPersons() {
        when(personService.getAllPersons()).thenReturn(List.of(person));

        List<Person> persons = personController.getAllPersons();

        assertFalse(persons.isEmpty());
        assertEquals(1, persons.size());
        verify(personService, times(1)).getAllPersons();
    }

    @Test
    void testGetPersonByEmail_Found() {
        when(personService.getPersonByEmail("test@example.com")).thenReturn(Optional.of(person));

        ResponseEntity<Person> response = personController.getPersonByEmail("test@example.com");

        assertEquals(200, response.getStatusCode().value());
        assertEquals(person, response.getBody());
    }

    @Test
    void testGetPersonByEmail_NotFound() {
        when(personService.getPersonByEmail("notfound@example.com")).thenReturn(Optional.empty());

        ResponseEntity<Person> response = personController.getPersonByEmail("notfound@example.com");

        assertEquals(404, response.getStatusCode().value());
        assertNull(response.getBody());
    }

    @Test
    void testCreatePerson_Success() {
        when(personService.savePerson(person)).thenReturn(person);

        ResponseEntity<Person> response = personController.createPerson(person);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(person, response.getBody());
    }

    @Test
    void testCreatePerson_BadRequest() {
        when(personService.savePerson(person)).thenThrow(IllegalArgumentException.class);

        ResponseEntity<Person> response = personController.createPerson(person);

        assertEquals(400, response.getStatusCode().value());
        assertNull(response.getBody());
    }

    @Test
    void testUpdatePerson_Success() {
        when(personService.updatePerson(eq("test@example.com"), any(Person.class))).thenReturn(person);

        ResponseEntity<Person> response = personController.updatePerson("test@example.com", person);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(person, response.getBody());
    }

    @Test
    void testUpdatePerson_NotFound() {
        when(personService.updatePerson(eq("test@example.com"), any(Person.class))).thenThrow(IllegalArgumentException.class);

        ResponseEntity<Person> response = personController.updatePerson("test@example.com", person);

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testLogin_Success() {
        when(personService.login("test@example.com", "password")).thenReturn(Optional.of(person));

        ResponseEntity<Person> response = personController.login("test@example.com", "password");

        assertEquals(200, response.getStatusCode().value());
        assertEquals(person, response.getBody());
    }

    @Test
    void testLogin_Unauthorized() {
        when(personService.login("test@example.com", "wrongpassword")).thenReturn(Optional.empty());

        ResponseEntity<Person> response = personController.login("test@example.com", "wrongpassword");

        assertEquals(401, response.getStatusCode().value());
    }

    @Test
    void testDeletePerson() {
        doNothing().when(personService).deletePerson("test@example.com");

        ResponseEntity<Void> response = personController.deletePerson("test@example.com");

        assertEquals(204, response.getStatusCode().value());
        verify(personService, times(1)).deletePerson("test@example.com");
    }
}