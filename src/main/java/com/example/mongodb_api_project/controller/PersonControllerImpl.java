package com.example.mongodb_api_project.controller;

import com.example.mongodb_api_project.dto.PersonInfoDto;
import com.example.mongodb_api_project.entity.PersonEntity;
import com.example.mongodb_api_project.exception_handler.MongoAPIException;
import com.example.mongodb_api_project.exception_handler.ValidationException;
import com.example.mongodb_api_project.service.IPersonService;
import com.example.mongodb_api_project.util.Validator;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



import java.util.Optional;


@RestController
@RequestMapping("/v1/api/persons")
public class PersonControllerImpl implements IPersonController {
    private static final Logger logger = LogManager.getLogger(PersonControllerImpl.class);
    private final IPersonService personService;

    @Autowired
    PersonControllerImpl(IPersonService services) {
        this.personService = services;
    }

    @Override
    @GetMapping("/list")
    public ResponseEntity<Page<PersonInfoDto>> getList(@RequestParam(defaultValue = "10",required = false) int size,
                                                       @RequestParam(defaultValue = "0",required = false) int page){
        Pageable p = PageRequest.of(page, size);
        return new ResponseEntity<>(personService.getList(p),HttpStatus.OK);
    }

    @Override
    @GetMapping("/{email}")
    public ResponseEntity<PersonInfoDto> getPerson(@PathVariable("email") String email) throws MongoAPIException {
        Optional<PersonInfoDto> person = personService.getPerson(email);
        if (person.isPresent()) {
            logger.info("{} info retrieved", email);
            return new ResponseEntity<>(person.get(), HttpStatus.OK);
        }
        throw new MongoAPIException(HttpStatus.NOT_FOUND, "Person not found in Db");
    }

    @Override
    @PostMapping()
    public ResponseEntity<String> addPerson(@RequestBody @Valid PersonInfoDto userInput, BindingResult result)
            throws ValidationException, MongoAPIException {
        Validator.validate(result);
        personService.addPerson(userInput);
        logger.info("New person added successfully");
        return new ResponseEntity<>("Person added to database", HttpStatus.CREATED);
    }

    @Override
    @PutMapping()
    public ResponseEntity<PersonEntity> updatePerson(@RequestBody @Valid PersonInfoDto userInput, BindingResult result)
            throws ValidationException, MongoAPIException {
        Validator.validate(result);
        PersonEntity updatedPerson = personService.updatePerson(userInput);
        logger.info("Person information updated");
        return new ResponseEntity<>(updatedPerson, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletePerson(@PathVariable String email) throws MongoAPIException {
        personService.deletePerson(email);
        logger.info("{} information deleted", email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}