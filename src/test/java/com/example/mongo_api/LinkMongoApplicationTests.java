package com.example.mongo_api;

import com.example.mongo_api.dto.PersonInfoDto;
import com.example.mongo_api.entity.PersonEntity;
import com.example.mongo_api.exception_handler.MongoAPIException;
import com.example.mongo_api.service.PersonServicesImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class LinkMongoApplicationTests {

	@Autowired
	PersonServicesImpl service;
	@Test
	void testAddPerson() throws MongoAPIException {
		service.addPerson(new PersonInfoDto("testMail12@domain.com","FirstNameTest","LastNameTest"));
		PersonEntity person =service.getPerson("testMail12@domain.com").get();
		Assertions.assertEquals("testMail12@domain.com",person.getEmail());
	}

	@Test
	void testGetPerson() throws MongoAPIException{
		Assertions.assertEquals(service.getPerson("testMail12@domain.com").get(),new PersonEntity("testMail12@domain.com","FirstNameTest","LastNameTest"));
	}

	@Test
	void testUpdatePerson() throws MongoAPIException {
		service.updatePerson(new PersonInfoDto("testMail12@domain.com","updatedTestName","updatedLastName"));
		Assertions.assertEquals("updatedTestName",service.getPerson("testMail12@domain.com").get().getFirstName());
	}

	@Test
	void testDeletePerson() throws MongoAPIException{
		service.deletePerson(new PersonInfoDto("testMail12@domain.com","updatedTestName","updatedLastName"));
		Assertions.assertThrows(MongoAPIException.class, () -> {
			service.getPerson("testMail12@domain.com");
		});
	}
}
