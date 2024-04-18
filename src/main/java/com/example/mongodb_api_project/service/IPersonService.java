package com.example.mongodb_api_project.service;

import com.example.mongodb_api_project.dto.PersonInfoDto;
import com.example.mongodb_api_project.entity.PersonEntity;
import com.example.mongodb_api_project.exception_handler.MongoAPIException;

import java.util.Optional;

public interface IPersonService {
    Optional<PersonEntity> getPerson(String name);

    void addPerson(PersonInfoDto person) throws MongoAPIException;

    void updatePerson(PersonInfoDto person) throws MongoAPIException;

    void deletePerson(String mail) throws MongoAPIException;


}
