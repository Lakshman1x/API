package com.example.mongo_api.service;

import com.example.mongo_api.dto.PersonInfoDto;
import com.example.mongo_api.entity.PersonEntity;
import com.example.mongo_api.exception_handler.MongoAPIException;
import com.example.mongo_api.repo.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
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

    public PersonEntity addPerson(PersonInfoDto person) throws MongoAPIException {
        try {
            return personRepository.insert(convertToPerson(person));
        } catch (Exception e) {
            throw new MongoAPIException(400, "Person with same email already exists");
        }
    }



    public PersonEntity updatePerson(PersonInfoDto person) throws MongoAPIException {
        Optional<PersonEntity> existingPerson = personRepository.findById(person.getEmail());
        if (existingPerson.isPresent()) {
            return personRepository.save(convertToPerson(person));
        }
        throw new MongoAPIException(400,"Person does not exist in the database");
    }

    public Boolean deletePerson(PersonInfoDto person) throws MongoAPIException {
        Optional<PersonEntity> existingPerson = personRepository.findById(person.getEmail());
        if (existingPerson.isPresent() && existingPerson.get().equals(convertToPerson(person))) {
            personRepository.delete(convertToPerson(person));
            return true;
        }
        throw  new MongoAPIException(404,"Person not found in database");
    }

    private PersonEntity convertToPerson(PersonInfoDto input) {
        return new PersonEntity(input.getEmail(),input.getFirstName(), input.getLastName());
    }

}
