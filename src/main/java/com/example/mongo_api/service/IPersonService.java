package com.example.mongo_api.service;

import com.example.mongo_api.dto.PersonInfoDto;
import com.example.mongo_api.entity.PersonEntity;
import com.example.mongo_api.exception_handler.MongoAPIException;

import java.util.Optional;

public interface IPersonService {
    Optional<PersonEntity> getPerson(String name);

    PersonEntity addPerson(PersonInfoDto person) throws MongoAPIException;

    PersonEntity updatePerson(PersonInfoDto person) throws MongoAPIException;

    Boolean deletePerson(PersonInfoDto person) throws MongoAPIException;


}
