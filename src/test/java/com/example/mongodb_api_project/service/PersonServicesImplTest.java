package com.example.mongodb_api_project.service;

import com.example.mongodb_api_project.dto.PersonInfoDto;
import com.example.mongodb_api_project.entity.PersonEntity;
import com.example.mongodb_api_project.exception_handler.MongoAPIException;
import com.example.mongodb_api_project.repo.IPersonRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PersonServicesImplTest {
    String sampleEmail = "sampleTestMail@domain.com";
    String sampleFirstName = "samplefirstname";
    String sampleLastName = "samplelastname";
    @Mock
    private IPersonRepo personRepo;

    @InjectMocks
    private PersonServicesImpl personServices;
    private PersonEntity personEntity;
    private PersonInfoDto personInfoDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        personEntity = new PersonEntity(sampleEmail, sampleFirstName, sampleLastName);
        personInfoDto = new PersonInfoDto(sampleEmail, sampleFirstName, sampleLastName);
    }

    @Test
    void testGetPerson() {
        Optional<PersonEntity> optionalPersonEntity = Optional.of(personEntity);
        when(personRepo.findById(any())).thenReturn(optionalPersonEntity);
        assertEquals(personServices.getPerson(sampleEmail), optionalPersonEntity);
    }

    @Test
    void testAddPerson() throws MongoAPIException {
        personServices.addPerson(personInfoDto);
        verify(personRepo, times(1)).save(personEntity);
    }

    @Test
    void testUpdatePerson() throws MongoAPIException {
        when(personRepo.findById(any())).thenReturn(Optional.of(personEntity));
        personServices.updatePerson(personInfoDto);
        verify(personRepo, times(1)).save(personEntity);
    }

    @Test
    void testDeletePerson() throws MongoAPIException {
        when(personRepo.findById(any())).thenReturn(Optional.of(personEntity));
        personServices.deletePerson(sampleEmail);
        verify(personRepo, times(1)).deleteById(sampleEmail);
    }

    @Test
    void testAddPersonThrowsException() {
        when(personRepo.existsById(any())).thenReturn(true);
        assertThrows(MongoAPIException.class, () -> personServices.addPerson(personInfoDto));
    }

    @Test
    void testUpdatePersonThrowsException() {
        when(personRepo.findById(any())).thenReturn(Optional.empty());
        assertThrows(MongoAPIException.class, () -> personServices.updatePerson(personInfoDto));
    }

    @Test
    void testDeletePersonThrowsException() {
        when(personRepo.findById(any())).thenReturn(Optional.empty());
        assertThrows(MongoAPIException.class, () -> personServices.deletePerson(sampleEmail));
    }
}