package com.example.mongo_api.repo;

import com.example.mongo_api.Model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepo extends MongoRepository<Person, String> {
}
