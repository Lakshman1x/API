package com.example.mongodb_api_project.controller;

import com.example.mongodb_api_project.dto.PersonInfoDto;
import com.example.mongodb_api_project.entity.PersonEntity;
import com.example.mongodb_api_project.exception_handler.MongoAPIException;
import com.example.mongodb_api_project.exception_handler.ValidationException;
import com.example.mongodb_api_project.service.IPersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.example.mongodb_api_project.testdata.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.doThrow;

class PersonControllerImplTest {
    @Mock
    private IPersonService personService;
    @Mock
    private BindingResult bindingResult;
    @InjectMocks
    private PersonControllerImpl personController;

    private PersonInfoDto personInfoDto;

    @BeforeEach
    public void setUp() {
        personInfoDto = new PersonInfoDto(SAMPLE_EMAIL, SAMPLE_FIRSTNAME, SAMPLE_LASTNAME);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetList() {
        List<PersonInfoDto> personList = new ArrayList<>();
        personList.add(personInfoDto);
        when(personService.getList(any())).thenReturn(new PageImpl<>(personList));
        ResponseEntity<Page<PersonInfoDto>> responseEntity = personController.getList(10,0);
        assertEquals(personList, responseEntity.getBody().getContent());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetPerson() throws MongoAPIException {
        when(personService.getPerson(any())).thenReturn(Optional.of(personInfoDto));
        ResponseEntity<PersonInfoDto> response = personController.getPerson(SAMPLE_EMAIL);
        assertEquals(SAMPLE_EMAIL, Objects.requireNonNull(response.getBody()).getEmail());
    }

    @Test
    void testAddPerson() throws MongoAPIException, ValidationException {
        ResponseEntity<String> response = personController.addPerson(personInfoDto, bindingResult);
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
        ResponseEntity<Void> response = personController.deletePerson(SAMPLE_EMAIL);
        verify(personService, times(1)).deletePerson(SAMPLE_EMAIL);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testGetPersonThrowsMongoAPIException() {
        when(personService.getPerson(any())).thenReturn(Optional.empty());
        assertThrows(MongoAPIException.class, () -> personController.getPerson(SAMPLE_EMAIL));
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
        assertThrows(MongoAPIException.class, () -> personController.deletePerson(SAMPLE_EMAIL));
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