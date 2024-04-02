package com.example.LinkMongo.Repo;

import com.example.LinkMongo.Model.Person;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyRepo extends MongoRepository<Person, String> {
}
