package com.example.mongo_api.repo;

import com.example.mongo_api.entity.PersonEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepo extends MongoRepository<PersonEntity, String> {
}
