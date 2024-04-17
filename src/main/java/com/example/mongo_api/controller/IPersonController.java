package com.example.mongo_api.controller;

import com.example.mongo_api.dto.PersonInfoDto;
import com.example.mongo_api.dto.Response;
import com.example.mongo_api.entity.PersonEntity;
import com.example.mongo_api.exception_handler.MongoAPIException;
import com.example.mongo_api.exception_handler.ValidationException;
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
