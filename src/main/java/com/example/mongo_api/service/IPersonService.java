package com.example.mongo_api.service;

import com.example.mongo_api.dto.PersonInfoDto;
import com.example.mongo_api.entity.PersonEntity;
import com.example.mongo_api.exception_handler.MongoAPIException;

import java.util.Optional;

public interface IPersonService {
    Optional<PersonEntity> getPerson(String name);

    void addPerson(PersonInfoDto person) throws MongoAPIException;

    void updatePerson(PersonInfoDto person) throws MongoAPIException;

    void deletePerson(String mail) throws MongoAPIException;


}
