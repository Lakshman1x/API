package com.example.mongo_api.services;

import com.example.mongo_api.dto.PersonInfoDto;
import com.example.mongo_api.entity.Person;

import java.util.Optional;

public interface IPersonService {
    Optional<Person> getPerson(String name);
    Person addPerson(PersonInfoDto person);
    Person updatePerson(PersonInfoDto person);
    Boolean deletePerson(PersonInfoDto person);


}
