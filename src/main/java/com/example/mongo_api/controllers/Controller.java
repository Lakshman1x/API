package com.example.mongo_api.controllers;

import com.example.mongo_api.dto.UserInput;
import com.example.mongo_api.Model.Person;
import com.example.mongo_api.util.Response;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface Controller {
    ResponseEntity<List<Person>> getList();
    ResponseEntity<?> getPerson(@PathVariable("name") String name);

    ResponseEntity<Response> addPerson(@RequestBody @Valid UserInput userInput, BindingResult result);

    ResponseEntity<Response> updatePerson(@RequestBody @Valid UserInput userInput, BindingResult result );

    ResponseEntity<Response> deletePerson(@RequestBody @Valid UserInput userInput, BindingResult result);


}
