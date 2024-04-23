package com.example.mongodb_api_project.controller;

import com.example.mongodb_api_project.dto.PersonInfoDto;
import com.example.mongodb_api_project.entity.PersonEntity;
import com.example.mongodb_api_project.exception_handler.MongoAPIException;
import com.example.mongodb_api_project.exception_handler.ValidationException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface IPersonController {
    ResponseEntity<Page<PersonInfoDto>> getList(@RequestParam int size, @RequestParam int page );

    ResponseEntity<PersonInfoDto> getPerson(@PathVariable("name") String name) throws MongoAPIException;

    ResponseEntity<String> addPerson(@RequestBody @Valid PersonInfoDto userInput, BindingResult result) throws ValidationException, MongoAPIException;

    ResponseEntity<PersonEntity> updatePerson(@RequestBody @Valid PersonInfoDto userInput, BindingResult result) throws ValidationException, MongoAPIException;

    ResponseEntity deletePerson(String mail) throws ValidationException, MongoAPIException;


}
