package com.example.mongodb_api_project.service;

import com.example.mongodb_api_project.dto.PersonInfoDto;
import com.example.mongodb_api_project.entity.PersonEntity;
import com.example.mongodb_api_project.exception_handler.MongoAPIException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface IPersonService {
    Optional<PersonInfoDto> getPerson(String mail);

    Page<PersonInfoDto> getList(Pageable page);

    void addPerson(PersonInfoDto person) throws MongoAPIException;

    PersonEntity updatePerson(PersonInfoDto person) throws MongoAPIException;

    void deletePerson(String mail) throws MongoAPIException;

}