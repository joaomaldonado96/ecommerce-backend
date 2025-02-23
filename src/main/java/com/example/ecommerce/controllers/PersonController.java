package com.example.ecommerce.controllers;
import com.example.ecommerce.dtos.PersonRequestDTO;
import com.example.ecommerce.dtos.PersonResponseDTO;
import com.example.ecommerce.entities.Person;
import com.example.ecommerce.services.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/persons")
public class PersonController  {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<PersonResponseDTO> getAllPersons() {
        return personService.getAllPersons().stream()
                .map(PersonResponseDTO::fromEntity).collect(Collectors.toList());
    }

    @GetMapping("/{email}")
    public ResponseEntity<PersonResponseDTO> getPersonByEmail(@PathVariable String email) {
        Optional<Person> person = personService.getPersonByEmail(email);
        return person.map(p -> ResponseEntity.ok(PersonResponseDTO.fromEntity(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PersonResponseDTO> createPerson(@RequestBody PersonRequestDTO personRequestDTO) {
        try {
            Person person = personRequestDTO.ToEntity();
            Person savedPerson = personService.savePerson(person);
            return ResponseEntity.ok(PersonResponseDTO.fromEntity(savedPerson));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{email}")
    public ResponseEntity<PersonResponseDTO> updatePerson(@PathVariable String email, @RequestBody PersonRequestDTO updatedPersonDTO) {
        try {
            Person updatedPerson = updatedPersonDTO.ToEntity();
            Person person = personService.updatePerson(email, updatedPerson);
            return ResponseEntity.ok(PersonResponseDTO.fromEntity(person));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<PersonResponseDTO> login(@RequestParam String email, @RequestParam String password) {
        return personService.login(email, password)
                .map(p -> ResponseEntity.ok(PersonResponseDTO.fromEntity(p)))
                .orElseGet(() -> ResponseEntity.status(401).build());
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletePerson(@PathVariable String email) {
        personService.deletePerson(email);
        return ResponseEntity.noContent().build();
    }
}