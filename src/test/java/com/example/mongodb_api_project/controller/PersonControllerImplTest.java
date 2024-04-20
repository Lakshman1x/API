package com.example.mongodb_api_project.controller;

import com.example.mongodb_api_project.dto.PersonInfoDto;
import com.example.mongodb_api_project.dto.Response;
import com.example.mongodb_api_project.entity.PersonEntity;
import com.example.mongodb_api_project.exception_handler.MongoAPIException;
import com.example.mongodb_api_project.exception_handler.ValidationException;
import com.example.mongodb_api_project.repo.IPersonRepo;
import com.example.mongodb_api_project.service.PersonServicesImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PersonControllerImplTest {

    private final String sampleEmail = "sampleTestMail@domain.com";
    private final String sampleFirstName = "samplefirstname";
    private final String sampleLastName = "samplelastname";
    @Mock
    private PersonServicesImpl personService;
    @Mock
    private BindingResult bindingResult;
    @Mock
    private IPersonRepo personRepo;
    @InjectMocks
    private PersonControllerImpl personController;
    private PersonEntity personEntity;
    private PersonInfoDto personInfoDto;

    @BeforeEach
    public void setUp() {
        personEntity = new PersonEntity(sampleEmail, sampleFirstName, sampleLastName);
        personInfoDto = new PersonInfoDto(sampleEmail, sampleFirstName, sampleLastName);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetList() {
        List<PersonEntity> personList = new ArrayList<>();
        personList.add(personEntity);
        when(personRepo.findAll()).thenReturn(personList);
        ResponseEntity<List<PersonEntity>> responseEntity = personController.getList();
        assertEquals(personList, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetPerson() throws MongoAPIException {
        when(personService.getPerson(any())).thenReturn(Optional.of(personEntity));
        ResponseEntity<PersonInfoDto> response = personController.getPerson(sampleEmail);
        assertEquals(sampleEmail, Objects.requireNonNull(response.getBody()).getEmail());
    }

    @Test
    void testAddPerson() throws MongoAPIException, ValidationException {
        ResponseEntity<Response> response = personController.addPerson(personInfoDto, bindingResult);
        verify(personService, times(1)).addPerson(personInfoDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testUpdatePerson() throws ValidationException, MongoAPIException {
        ResponseEntity<PersonEntity> response = personController.updatePerson(personInfoDto, bindingResult);
        verify(personService, times(1)).updatePerson(personInfoDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeletePerson() throws MongoAPIException {
        ResponseEntity response = personController.deletePerson(sampleEmail);
        verify(personService, times(1)).deletePerson(sampleEmail);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testGetPersonThrowsMongoAPIException() {
        when(personService.getPerson(any())).thenReturn(Optional.empty());
        assertThrows(MongoAPIException.class, () -> personController.getPerson(sampleEmail));
    }

    @Test
    void testAddPersonThrowsMongoAPIException() throws MongoAPIException {
        doThrow(MongoAPIException.class).when(personService).addPerson(any());
        assertThrows(MongoAPIException.class, () -> personController.addPerson(personInfoDto, bindingResult));
    }

    @Test
    void testUpdatePersonThrowsMongoAPIException() throws MongoAPIException {
        when(personService.updatePerson(any())).thenThrow(new MongoAPIException(HttpStatus.BAD_REQUEST, "Not updated, Person not found in the database, "));
        assertThrows(MongoAPIException.class, () -> personController.updatePerson(personInfoDto, bindingResult));
    }

    @Test
    void testDeletePersonThrowsMongoAPIException() throws MongoAPIException {
        doThrow(MongoAPIException.class).when(personService).deletePerson(any());
        assertThrows(MongoAPIException.class, () -> personController.deletePerson(sampleEmail));
    }


    @Test
    void testAddPersonThrowsValidationException() throws MongoAPIException {
        when(bindingResult.hasErrors()).thenReturn(true);
        assertThrows(ValidationException.class, () -> personController.addPerson(personInfoDto, bindingResult));
        verify(personService, never()).addPerson(any());
    }

    @Test
    void testUpdatePersonThrowsValidationException() throws MongoAPIException {
        when(bindingResult.hasErrors()).thenReturn(true);
        assertThrows(ValidationException.class, () -> personController.updatePerson(personInfoDto, bindingResult));
        verify(personService, never()).updatePerson(any());
    }

}