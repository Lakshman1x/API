package com.example.mongodb_api_project;

import com.example.mongodb_api_project.dto.PersonInfoDto;
import com.example.mongodb_api_project.entity.PersonEntity;
import com.example.mongodb_api_project.exception_handler.MongoAPIException;
import com.example.mongodb_api_project.service.PersonServicesImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class LinkMongoApplicationTests {

    private final String testMail = "testMail12@domain.com";
    private final String testFirstName = "testFirstName";
    private final String testLastName = "testLastName";
    private final String testUpdatedFirstName = "updatedTestName";

    @Autowired
    PersonServicesImpl service;

    @Test
    void testAddPerson() throws MongoAPIException {
        service.addPerson(new PersonInfoDto(testMail, testFirstName, testLastName));
        PersonEntity person = service.getPerson(testMail).get();
        Assertions.assertEquals(testMail, person.getEmail());
    }

    @Test
    void testGetPerson() throws MongoAPIException {
        Assertions.assertEquals(service.getPerson(testMail).get(), new PersonEntity(testMail, testFirstName, testLastName));
    }

    @Test
    void testUpdatePerson() throws MongoAPIException {
        service.updatePerson(new PersonInfoDto(testMail, testUpdatedFirstName, testLastName));
        Assertions.assertEquals(testUpdatedFirstName, service.getPerson(testMail).get().getFirstName());
    }

    @Test
    void testDeletePerson() throws MongoAPIException {
        service.deletePerson(testMail);
        Assertions.assertThrows(MongoAPIException.class, () -> {
            service.getPerson(testMail);
        });
    }
}
