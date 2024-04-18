package com.example.mongodb_api_project.controller;

import com.example.mongodb_api_project.dto.PersonInfoDto;
import com.example.mongodb_api_project.dto.Response;
import com.example.mongodb_api_project.entity.PersonEntity;
import com.example.mongodb_api_project.exception_handler.MongoAPIException;
import com.example.mongodb_api_project.exception_handler.ValidationException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IPersonController {
    ResponseEntity<List<PersonEntity>> getList();

    ResponseEntity<PersonInfoDto> getPerson(@PathVariable("name") String name) throws MongoAPIException;

    ResponseEntity<Response> addPerson(@RequestBody @Valid PersonInfoDto userInput, BindingResult result) throws ValidationException, MongoAPIException;

    ResponseEntity<Response> updatePerson(@RequestBody @Valid PersonInfoDto userInput, BindingResult result) throws ValidationException, MongoAPIException;

    ResponseEntity<Response> deletePerson(String mail) throws ValidationException, MongoAPIException;


}
