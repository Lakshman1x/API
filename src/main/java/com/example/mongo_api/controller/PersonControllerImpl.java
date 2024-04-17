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
@RequestMapping("/v1/api/person")
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

    @GetMapping("/{email}")
    public ResponseEntity<PersonInfoDto> getPerson(@PathVariable("email") String email) throws MongoAPIException {
        Optional<PersonEntity> person = personService.getPerson(email);
        if (person.isPresent()) {
            logger.info("{} info retrieved", email);
            PersonEntity temp = person.get();
            return new ResponseEntity<>(new PersonInfoDto(temp.getEmail(), temp.getFirstName(), temp.getLastName()), HttpStatus.OK);
        } else {
            throw new MongoAPIException(HttpStatus.NOT_FOUND, "Person not found in Db");
        }

    }

    @PostMapping()
    public ResponseEntity<Response> addPerson(@RequestBody @Valid PersonInfoDto userInput, BindingResult result) throws ValidationException, MongoAPIException {
        Validator.validate(result);
        personService.addPerson(userInput);
        Response temp = new Response(HttpStatus.CREATED, "Person added to database");
        return new ResponseEntity<>(temp, HttpStatus.CREATED);

    }


    @PutMapping()
    public ResponseEntity<Response> updatePerson(@RequestBody @Valid PersonInfoDto userInput, BindingResult result) throws ValidationException, MongoAPIException {
        Validator.validate(result);
        personService.updatePerson(userInput);
        logger.info("Person information updated");
        return new ResponseEntity<>(new Response(HttpStatus.OK, "Details updated"), HttpStatus.OK);
    }


    @DeleteMapping("/{email}")
    public ResponseEntity<Response> deletePerson(@PathVariable String email) throws MongoAPIException {
        personService.deletePerson(email);
        logger.info("{} information deleted", email);
        return new ResponseEntity<>(new Response(HttpStatus.NO_CONTENT, "Person information deleted"), HttpStatus.OK);

    }
}