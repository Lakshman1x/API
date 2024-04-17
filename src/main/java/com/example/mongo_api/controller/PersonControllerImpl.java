package com.example.mongo_api.controller;

import com.example.mongo_api.dto.PersonInfoDto;
import com.example.mongo_api.dto.Response;
import com.example.mongo_api.entity.PersonEntity;
import com.example.mongo_api.exception_handler.MongoAPIException;
import com.example.mongo_api.exception_handler.ValidationException;
import com.example.mongo_api.repo.PersonRepo;
import com.example.mongo_api.service.IPersonService;
import com.example.mongo_api.service.PersonServicesImpl;
import com.example.mongo_api.util.Validator;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/")
public class PersonControllerImpl implements IPersonController {
    private static final Logger logger = LogManager.getLogger(PersonControllerImpl.class);
    private final PersonRepo personRepository;
    private final IPersonService personService;

    @Autowired
    PersonControllerImpl(PersonRepo repo, PersonServicesImpl services) {
        this.personRepository = repo;
        this.personService = services;
    }


    @GetMapping("list")
    public ResponseEntity<List<PersonEntity>> getList() {
        return new ResponseEntity<>(personRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("get/{email}")
    public ResponseEntity<PersonEntity> getPerson(@PathVariable("email") String email) throws MongoAPIException {
        Optional<PersonEntity> person = personService.getPerson(email);
        if (person.isPresent()) {
            logger.info("{} info retrieved", email);
            return new ResponseEntity<>(person.get(), HttpStatus.OK);
        } else {
            throw new MongoAPIException(404,"Person not found in Db");
        }

    }

    @PostMapping("add")
    public ResponseEntity<Response> addPerson(@RequestBody @Valid PersonInfoDto userInput, BindingResult result) throws ValidationException, MongoAPIException {
        Validator.validate(result);

            PersonEntity person = personService.addPerson(userInput);
            if (person == null) {
                throw new MongoAPIException(400,"Person already exists in the database");

            } else {
                Response temp =new Response(201,"Person added to database");
                return new ResponseEntity<>(temp,HttpStatus.CREATED);
            }
    }


    @PutMapping("update")
    public ResponseEntity<Response> updatePerson(@RequestBody @Valid PersonInfoDto userInput, BindingResult result) throws ValidationException, MongoAPIException {
        Validator.validate(result);
        personService.updatePerson(userInput);
        logger.info("Person information updated");
        return new ResponseEntity<>(new Response(200, "Details updated"), HttpStatus.OK);
    }


    @DeleteMapping("delete")
    public ResponseEntity<Response> deletePerson(@RequestBody @Valid PersonInfoDto userInput, BindingResult result) throws ValidationException, MongoAPIException {

        personService.deletePerson(userInput);
        logger.info("{} information deleted", userInput.getFirstName());
        return new ResponseEntity<>(new Response(200,"Person information deleted"), HttpStatus.OK);
    }

}