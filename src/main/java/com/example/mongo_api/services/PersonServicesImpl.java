package com.example.mongo_api.services;

import com.example.mongo_api.dto.PersonInfoDto;
import com.example.mongo_api.entity.Person;
import com.example.mongo_api.repo.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonServicesImpl implements IPersonService {

    PersonRepo repo;

    @Autowired
    PersonServicesImpl(PersonRepo repo) {
        this.repo = repo;
    }

    public Optional<Person> getPerson(String name) {
        return repo.findById(name);

    }

    public Person addPerson(PersonInfoDto person) {
        Optional<Person> existingPerson = repo.findById(person.getFirstName());
        if (existingPerson.isPresent()) {
            return null;
        }
        return repo.insert(convertToPerson(person));
    }


    public Person updatePerson(PersonInfoDto person) {
        Optional<Person> existingPerson = repo.findById(person.getFirstName());
        if (existingPerson.isPresent()) {
            return repo.save(convertToPerson(person));
        }
        return null;
    }

    public Boolean deletePerson(PersonInfoDto person) {
        Optional<Person> existingPerson = repo.findById(person.getFirstName());
        if (existingPerson.isPresent() && existingPerson.get().equalsPerson(convertToPerson(person))) {
            repo.delete(convertToPerson(person));
            return true;
        }
        return false;
    }

    private Person convertToPerson(PersonInfoDto input) {
        return new Person(input.getFirstName(), input.getLastName());
    }

}
