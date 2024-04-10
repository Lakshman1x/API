package com.example.mongo_api.services;

import com.example.mongo_api.dto.UserInput;
import com.example.mongo_api.Model.Person;

public interface ServiceInterface {
    Person getPerson(String name);
    Person addPerson(UserInput person);
    Person updatePerson(UserInput person);
    Boolean deletePerson(UserInput person);


}
