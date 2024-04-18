package com.example.mongodb_api_project.repo;

import com.example.mongodb_api_project.entity.PersonEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersonRepo extends MongoRepository<PersonEntity, String> {
}
