package com.example.mongo_api.controllers;

import com.example.mongo_api.dto.PersonInfoDto;
import com.example.mongo_api.entity.Person;
import com.example.mongo_api.dto.Response;
import com.example.mongo_api.exception_handler.ValidationException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IController {
    ResponseEntity<List<Person>> getList();
    ResponseEntity<?> getPerson(@PathVariable("name") String name);

    ResponseEntity<Response> addPerson(@RequestBody @Valid PersonInfoDto userInput, BindingResult result) throws ValidationException;

    ResponseEntity<Response> updatePerson(@RequestBody @Valid PersonInfoDto userInput, BindingResult result )throws ValidationException;

    ResponseEntity<Response> deletePerson(@RequestBody @Valid PersonInfoDto userInput, BindingResult result)throws ValidationException;


}
