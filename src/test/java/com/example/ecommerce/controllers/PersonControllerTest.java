package com.example.ecommerce.controllers;

import com.example.ecommerce.dtos.PersonRequestDTO;
import com.example.ecommerce.dtos.PersonResponseDTO;
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
    private PersonRequestDTO personRequestDTO;
    private PersonResponseDTO personResponseDTO;

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

        personRequestDTO = new PersonRequestDTO("test@example.com", "securepassword", "Juan", "123 Test Street", "1234567890", false);
        personResponseDTO = new PersonResponseDTO("test@example.com", "Juan", "123 Test Street", "1234567890", false, person.getCreatedAt());
    }

    @Test
    void testGetAllPersons() {
        when(personService.getAllPersons()).thenReturn(List.of(person));

        List<PersonResponseDTO> persons = personController.getAllPersons();

        assertFalse(persons.isEmpty());
        assertEquals(1, persons.size());
        verify(personService, times(1)).getAllPersons();
    }

    @Test
    void testGetPersonByEmail_Found() {
        when(personService.getPersonByEmail("test@example.com")).thenReturn(Optional.of(person));

        ResponseEntity<PersonResponseDTO> response = personController.getPersonByEmail("test@example.com");

        assertEquals(200, response.getStatusCode().value());
        assertEquals(personResponseDTO.getEmail(), response.getBody().getEmail());
    }

    @Test
    void testGetPersonByEmail_NotFound() {
        when(personService.getPersonByEmail("notfound@example.com")).thenReturn(Optional.empty());

        ResponseEntity<PersonResponseDTO> response = personController.getPersonByEmail("notfound@example.com");

        assertEquals(404, response.getStatusCode().value());
        assertNull(response.getBody());
    }

    @Test
    void testCreatePerson_Success() {
        when(personService.savePerson(any(Person.class))).thenReturn(person);

        ResponseEntity<PersonResponseDTO> response = personController.createPerson(personRequestDTO);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(personResponseDTO.getEmail(), response.getBody().getEmail());
    }

    @Test
    void testCreatePerson_BadRequest() {
        when(personService.savePerson(any(Person.class))).thenThrow(IllegalArgumentException.class);

        ResponseEntity<PersonResponseDTO> response = personController.createPerson(personRequestDTO);

        assertEquals(400, response.getStatusCode().value());
        assertNull(response.getBody());
    }

    @Test
    void testUpdatePerson_Success() {
        when(personService.updatePerson(eq("test@example.com"), any(Person.class))).thenReturn(person);

        ResponseEntity<PersonResponseDTO> response = personController.updatePerson("test@example.com", personRequestDTO);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(personResponseDTO.getEmail(), response.getBody().getEmail());
    }

    @Test
    void testUpdatePerson_NotFound() {
        when(personService.updatePerson(eq("test@example.com"), any(Person.class))).thenThrow(IllegalArgumentException.class);

        ResponseEntity<PersonResponseDTO> response = personController.updatePerson("test@example.com", personRequestDTO);

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testLogin_Success() {
        when(personService.login("test@example.com", "password")).thenReturn(Optional.of(person));

        ResponseEntity<PersonResponseDTO> response = personController.login("test@example.com", "password");

        assertEquals(200, response.getStatusCode().value());
        assertEquals(personResponseDTO.getEmail(), response.getBody().getEmail());
    }

    @Test
    void testLogin_Unauthorized() {
        when(personService.login("test@example.com", "wrongpassword")).thenReturn(Optional.empty());

        ResponseEntity<PersonResponseDTO> response = personController.login("test@example.com", "wrongpassword");

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