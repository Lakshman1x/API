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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static com.example.mongodb_api_project.testdata.TestConstants.SAMPLE_FIRSTNAME;
import static com.example.mongodb_api_project.testdata.TestConstants.SAMPLE_LASTNAME;
import static com.example.mongodb_api_project.testdata.TestConstants.SAMPLE_EMAIL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

class PersonServicesImplTest {

    @Mock
    private IPersonRepo personRepo;

    @InjectMocks
    private PersonServicesImpl personServices;
    private PersonEntity personEntity;
    private PersonInfoDto personInfoDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        personEntity = new PersonEntity(SAMPLE_EMAIL, SAMPLE_FIRSTNAME, SAMPLE_LASTNAME);
        personInfoDto = new PersonInfoDto(SAMPLE_EMAIL, SAMPLE_FIRSTNAME, SAMPLE_LASTNAME);
    }

    @Test
    void testGetList(){
        Page<PersonEntity> sample = new PageImpl<>(Collections.singletonList(personEntity));
        Page<PersonInfoDto> expected = new PageImpl<>(Collections.singletonList(personInfoDto));
        when(personRepo.findAll(any(Pageable.class))).thenReturn(sample);
        Page<PersonInfoDto> result =personServices.getList(mock(Pageable.class));
        assertEquals(result,expected);
    }

    @Test
    void testGetPerson() {
        Optional<PersonInfoDto> optionalPersonInfoDto = Optional.of(personInfoDto);
        Optional<PersonEntity> optionalPersonEntity = Optional.of(personEntity);
        when(personRepo.findById(any())).thenReturn(optionalPersonEntity);
        assertEquals(personServices.getPerson(SAMPLE_EMAIL), optionalPersonInfoDto);
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
        personServices.deletePerson(SAMPLE_EMAIL);
        verify(personRepo, times(1)).deleteById(SAMPLE_EMAIL);
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
        assertThrows(MongoAPIException.class, () -> personServices.deletePerson(SAMPLE_EMAIL));
    }
}