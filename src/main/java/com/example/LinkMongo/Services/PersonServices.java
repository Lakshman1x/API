package com.example.LinkMongo.Services;

import com.example.LinkMongo.Controller.PersonController;
import com.example.LinkMongo.Model.Person;
import com.example.LinkMongo.Repo.PersonRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class PersonServices {

    @Autowired
    PersonRepo repo;
    public Person getPerson(@PathVariable("name") String name) {
        Optional<Person> res= repo.findById(name);

        return res.orElse(null);
    }

    public  Person addPerson(Person person){
        Optional<Person> existingPerson = repo.findById(person.getFirstName());
        if(existingPerson.isPresent()){

            return null;
        }
        return repo.insert(person);
    }


    public  Person updatePerson(Person person){
        Optional<Person> existingPerson = repo.findById(person.getFirstName());
        if(existingPerson.isPresent()){
            return repo.save(person);
        }
        return  null;
    }

    public Boolean deletePerson(Person person){
        Optional<Person> existingPerson = repo.findById(person.getFirstName());
        if(existingPerson.isPresent() && existingPerson.get().equals(person)){
            repo.delete(person);
            return true;
        }
        return  false;
    }

}
