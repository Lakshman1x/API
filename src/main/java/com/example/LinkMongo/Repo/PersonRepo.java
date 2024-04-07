package com.example.LinkMongo.Repo;

import com.example.LinkMongo.Model.Pojo.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepo extends MongoRepository<Person, String> {
}
