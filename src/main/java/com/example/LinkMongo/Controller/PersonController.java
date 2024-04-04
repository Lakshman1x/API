package com.example.LinkMongo.Controller;

import com.example.LinkMongo.Model.Person;
import com.example.LinkMongo.Repo.PersonRepo;
import com.example.LinkMongo.Services.PersonServices;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/")

public class PersonController {
    private static final Logger logger = LogManager.getLogger(PersonController.class);
    @Autowired
    PersonRepo repo;

    @Autowired
    PersonServices services;

    @GetMapping("list")
    public  ResponseEntity<List<Person>> getList(){
        return  new ResponseEntity<>(repo.findAll(),HttpStatus.OK);
    }


    @GetMapping("get/{name}")
    public ResponseEntity<?>getPerson(@PathVariable("name") String name) {
        Person person =services.getPerson(name);
        if(person!=null){
            logger.info(name+" info retrieved");
            return  new ResponseEntity<>(person,HttpStatus.OK);
        }
        logger.info(name+"Person not found in database");
        return  new ResponseEntity<>("Person not found in database",HttpStatus.NOT_FOUND);
    }


    @PostMapping("add")
    public  ResponseEntity<?> addPerson(@RequestBody @Valid Person userInput) {
        Person person= services.addPerson(userInput);
        if(person==null){
            logger.info(userInput.getFirstName()+" Person already exists in the database ");
            return  new ResponseEntity<>("Person already exists in the database",HttpStatus.BAD_REQUEST);
        }
        logger.info(person.getFirstName()+" Person added to database");
        return new ResponseEntity<>(person,HttpStatus.OK);
    }


    @PutMapping("update")
    public ResponseEntity<?> updatePerson(@RequestBody @Valid Person userInput){
        Person person = services.updatePerson(userInput);
        if(person!=null){
            logger.info(person.getFirstName()+" information updated");
            return  new ResponseEntity<>(person,HttpStatus.OK);
        }
        logger.info("Person "+userInput.getFirstName()+" not found in database");
        return  new ResponseEntity<>("Person does not exist in the database",HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("delete")
    public ResponseEntity<?> deletePerson(@RequestBody @Valid Person userInput){
        Boolean result =services.deletePerson(userInput);
        if(result){logger.info("Person "+userInput.getFirstName()+" information deleted");
            return new ResponseEntity<>("User delete",HttpStatus.OK);
        }else{
            logger.info("Person "+userInput.getFirstName()+" does not exist");
          return new ResponseEntity<>("Person does not exist",HttpStatus.NOT_FOUND);
        }

    }


}
