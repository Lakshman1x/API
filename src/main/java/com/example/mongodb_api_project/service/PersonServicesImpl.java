package com.example.mongodb_api_project.service;

import com.example.mongodb_api_project.dto.PersonInfoDto;
import com.example.mongodb_api_project.entity.PersonEntity;
import com.example.mongodb_api_project.exception_handler.MongoAPIException;
import com.example.mongodb_api_project.repo.IPersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonServicesImpl implements IPersonService {

    private final IPersonRepo personRepository;

    @Autowired
    PersonServicesImpl(IPersonRepo repo) {
        this.personRepository = repo;
    }

    @Override
    public Optional<PersonInfoDto> getPerson(String email) {
        Optional<PersonEntity> foundPerson = personRepository.findById(email);
        return foundPerson.map(this::convertToPersonInfoDto);
    }

    @Override
    public void addPerson(PersonInfoDto person) throws MongoAPIException {
        if (!personRepository.existsById(person.getEmail())) {
            personRepository.save(convertToPerson(person));
            return;
        }
        throw new MongoAPIException(HttpStatus.BAD_REQUEST, "Person with same email already exists");
    }

   @Override
    public PersonEntity updatePerson(PersonInfoDto person) throws MongoAPIException {
        Optional<PersonEntity> existingPerson = personRepository.findById(person.getEmail());
        if (existingPerson.isPresent()) {
            return personRepository.save(convertToPerson(person));
        }
        throw new MongoAPIException(HttpStatus.NOT_FOUND, "Not updated, Person not found in the database, ");
    }

    @Override
    public void deletePerson(String mail) throws MongoAPIException {
        Optional<PersonEntity> existingPerson = personRepository.findById(mail);
        if (existingPerson.isPresent()) {
            personRepository.deleteById(mail);
            return;
        }
        throw new MongoAPIException(HttpStatus.NOT_FOUND, "No person with that email found");
    }

    @Override
    public Page<PersonInfoDto> getList(Pageable page) {
        return convertToPersonInfoDto(personRepository.findAll(page));
    }

    private PersonEntity convertToPerson(PersonInfoDto input) {
        return new PersonEntity(input.getEmail(), input.getFirstName(), input.getLastName());
    }

    private PersonInfoDto convertToPersonInfoDto(PersonEntity entity) {
        return new PersonInfoDto(entity.getEmail(), entity.getFirstName(), entity.getLastName());
    }

    private Page<PersonInfoDto> convertToPersonInfoDto(Page<PersonEntity> list) {
        return list.map(this::convertToPersonInfoDto);
    }
}