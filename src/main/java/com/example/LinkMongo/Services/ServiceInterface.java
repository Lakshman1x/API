package com.example.LinkMongo.Services;

import com.example.LinkMongo.Model.Dto.UserInput;
import com.example.LinkMongo.Model.Pojo.Person;

public interface ServiceInterface {
    Person getPerson(String name);
    Person addPerson(UserInput person);
    Person updatePerson(UserInput person);
    Boolean deletePerson(UserInput person);


}
