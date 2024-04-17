package com.example.mongo_api.service;

import com.example.mongo_api.dto.PersonInfoDto;
import com.example.mongo_api.entity.PersonEntity;
import com.example.mongo_api.exception_handler.MongoAPIException;
import com.example.mongo_api.repo.PersonRepo;
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
        try {
            personRepository.insert(convertToPerson(person));
        } catch (Exception e) {
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
