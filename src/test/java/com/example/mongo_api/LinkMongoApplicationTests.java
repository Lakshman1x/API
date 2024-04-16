package com.example.mongo_api;

import com.example.mongo_api.dto.PersonInfoDto;
import com.example.mongo_api.entity.Person;
import com.example.mongo_api.services.PersonServicesImpl;
import jakarta.validation.constraints.AssertTrue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;


@SpringBootTest
class LinkMongoApplicationTests {

	@Autowired
	PersonServicesImpl service;
	@Test
	void testAddPerson() {
		service.addPerson(new PersonInfoDto("TestName","TestLastName"));
		Person person =(service.getPerson("TestName").get());
		Assertions.assertEquals("TestName",person.getFirstName());

	}

}
