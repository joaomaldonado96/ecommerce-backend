package com.example.ecommerce.services;
import com.example.ecommerce.entities.Person;
import com.example.ecommerce.repositories.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Optional<Person> getPersonByEmail(String email) {
        return personRepository.findById(email);
    }

    @Transactional
    public Person savePerson(Person person) {
        if (personRepository.existsByEmail(person.getEmail())) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }
        if (person.getPassword().length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres");
        }
        return personRepository.save(person);
    }

    @Transactional
    public Person updatePerson(String email, Person updatedPerson) {
        return personRepository.findById(email).map(existingPerson -> {
            existingPerson.setName(updatedPerson.getName());
            existingPerson.setAddress(updatedPerson.getAddress());
            existingPerson.setPhone(updatedPerson.getPhone());
            return personRepository.save(existingPerson);
        }).orElseThrow(() -> new IllegalArgumentException("Persona no encontrada"));
    }

    public Optional<Person> login(String email, String password) {
        return personRepository.findByEmailAndPassword(email, password);
    }

    public void deletePerson(String email) {
        personRepository.deleteById(email);
    }
}
