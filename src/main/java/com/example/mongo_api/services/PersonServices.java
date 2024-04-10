package com.example.mongo_api.services;

import com.example.mongo_api.dto.UserInput;
import com.example.mongo_api.Model.Person;
import com.example.mongo_api.repo.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class PersonServices implements ServiceInterface {

    PersonRepo repo;
    @Autowired
    PersonServices(PersonRepo repo){
        this.repo=repo;
    }
    public Person getPerson(String name) {
        Optional<Person> res= repo.findById(name);

        return res.orElse(null);
    }

    public  Person addPerson(UserInput person){
        Optional<Person> existingPerson = repo.findById(person.getFirstName());
        if(existingPerson.isPresent()){

            return null;
        }
        return repo.insert(convertToPerson(person));
    }


    public  Person updatePerson(UserInput person){
        Optional<Person> existingPerson = repo.findById(person.getFirstName());
        if(existingPerson.isPresent()){
            return repo.save(convertToPerson(person));
        }
        return  null;
    }

    public Boolean deletePerson(UserInput person){
        Optional<Person> existingPerson = repo.findById(person.getFirstName());
        if(existingPerson.isPresent() && existingPerson.get().equals(convertToPerson(person))){
            repo.delete(convertToPerson(person));
            return true;
        }
        return  false;
    }

    private Person convertToPerson(UserInput input){
        return new Person(input.getFirstName(),input.getLastName());
    }

}
