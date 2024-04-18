package com.example.mongodb_api_project.service;

import com.example.mongodb_api_project.dto.PersonInfoDto;
import com.example.mongodb_api_project.entity.PersonEntity;
import com.example.mongodb_api_project.exception_handler.MongoAPIException;
import com.example.mongodb_api_project.repo.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonServicesImpl implements IPersonService {

    private final PersonRepo personRepository;

    @Autowired
    PersonServicesImpl(PersonRepo repo) {
        this.personRepository = repo;
    }

    public Optional<PersonEntity> getPerson(String email) {
        return personRepository.findById(email);

    }

    public void addPerson(PersonInfoDto person) throws MongoAPIException {

        if (!personRepository.existsById(person.getEmail())) {
            personRepository.save(convertToPerson(person));
        } else {
            throw new MongoAPIException(HttpStatus.BAD_REQUEST, "Person with same email already exists");
        }

    }


    public void updatePerson(PersonInfoDto person) throws MongoAPIException {
        Optional<PersonEntity> existingPerson = personRepository.findById(person.getEmail());
        if (existingPerson.isPresent()) {
            personRepository.save(convertToPerson(person));

            return;
        }
        throw new MongoAPIException(HttpStatus.NOT_MODIFIED, "Not updated, Person not found in the database, ");
    }

    public void deletePerson(String mail) throws MongoAPIException {
        Optional<PersonEntity> existingPerson = personRepository.findById(mail);
        if (existingPerson.isPresent()) {
            personRepository.deleteById(mail);
        } else {
            throw new MongoAPIException(HttpStatus.NOT_FOUND, "No person with that email found");
        }
    }

    private PersonEntity convertToPerson(PersonInfoDto input) {
        return new PersonEntity(input.getEmail(), input.getFirstName(), input.getLastName());
    }

}
