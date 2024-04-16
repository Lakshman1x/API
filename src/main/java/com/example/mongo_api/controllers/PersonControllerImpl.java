package com.example.mongo_api.controllers;

import com.example.mongo_api.dto.PersonInfoDto;
import com.example.mongo_api.entity.Person;
import com.example.mongo_api.dto.Response;
import com.example.mongo_api.repo.PersonRepo;
import com.example.mongo_api.services.PersonServicesImpl;
import jakarta.validation.Valid;
import com.example.mongo_api.exception_handler.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import com.example.mongo_api.util.Validator;


@RestController
@RequestMapping("/")
public class PersonControllerImpl implements IController {
    private static final Logger logger = LogManager.getLogger(PersonControllerImpl.class);
    PersonRepo repo;
    PersonServicesImpl services;

    @Autowired
    PersonControllerImpl(PersonRepo repo, PersonServicesImpl services){
        this.repo=repo;
        this.services=services;
    }


    @GetMapping("list")
    public  ResponseEntity<List<Person>> getList(){
        logger.info("List of persons retrieved");
        return  new ResponseEntity<>(repo.findAll(),HttpStatus.OK);
    }

    @GetMapping("get/{name}")
    public ResponseEntity<?> getPerson(@PathVariable("name") String name) {
        Optional<Person> person = services.getPerson(name);
        if (person.isPresent()) {
            logger.info("{} info retrieved", name);
            return new ResponseEntity<>(person, HttpStatus.OK);
        }
        logger.info("Person {} not found in database", name);
        return new ResponseEntity<>(new Response(false,"Person not found in database"),HttpStatus.BAD_REQUEST);
    }

    @PostMapping("add")
    public ResponseEntity<Response> addPerson(@RequestBody @Valid PersonInfoDto userInput, BindingResult result) throws ValidationException {
        Response res = Validator.validate(result);
        if(res.getStatus()){
            Person person = services.addPerson(userInput);
            if(person==null){
                res.setStatus(false);
                logger.info("Person already exists in the database");
                res.setMessage("Person already exists in the database");
                new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
            }else{
                logger.info("Person added to the database");
                res.setMessage("Person added to the database");
            }
        }
            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
    }



    @PutMapping("update")
    public ResponseEntity<Response> updatePerson(@RequestBody @Valid PersonInfoDto userInput, BindingResult result ) throws ValidationException{
        Response res = Validator.validate(result);
        if(res.getStatus()){
            Person person = services.updatePerson(userInput);
            if (person != null) {
                logger.info("{} information updated", person.getFirstName());
                res.setMessage("Details updated");
                return new ResponseEntity<>(res, HttpStatus.OK);
            }else{
                logger.info("Person {} not found in database", userInput.getFirstName());
                res.setMessage("Person "+ userInput.getFirstName() +" not found in database");
                res.setStatus(false);
                return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }



    @DeleteMapping("delete")
    public ResponseEntity<Response> deletePerson(@RequestBody @Valid PersonInfoDto userInput, BindingResult result) throws ValidationException{
        Response res= Validator.validate(result);
        if(!res.getStatus()){
            logger.warn("Validation error");
            return  new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }

        boolean delete = services.deletePerson(userInput);
        if(delete){
            logger.info("{} information deleted", userInput.getFirstName());
            res.setMessage("Details deleted");
            return new ResponseEntity<>(res, HttpStatus.NO_CONTENT);
        }else{
            logger.warn("Person {} does not exist", userInput.getFirstName());
            res.setMessage("Person does not exist");
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

    }

}