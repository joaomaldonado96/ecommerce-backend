package com.example.ecommerce.controllers;
import com.example.ecommerce.entities.Person;
import com.example.ecommerce.services.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/persons")
public class PersonController  {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/{email}")
    public ResponseEntity<Person> getPersonByEmail(@PathVariable String email) {
        Optional<Person> person = personService.getPersonByEmail(email);
        return person.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        try {
            Person savedPerson = personService.savePerson(person);
            return ResponseEntity.ok(savedPerson);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{email}")
    public ResponseEntity<Person> updatePerson(@PathVariable String email, @RequestBody Person updatedPerson) {
        try {
            Person person = personService.updatePerson(email, updatedPerson);
            return ResponseEntity.ok(person);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Person> login(@RequestParam String email, @RequestParam String password) {
        return personService.login(email, password)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(401).build());
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletePerson(@PathVariable String email) {
        personService.deletePerson(email);
        return ResponseEntity.noContent().build();
    }
}