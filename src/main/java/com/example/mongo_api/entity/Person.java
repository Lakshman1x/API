package com.example.mongo_api.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Person")
@Data
public class Person {

    @Id
    private final String firstName;


    private final String lastName;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }


    public boolean equalsPerson(Person obj) {
        return this.firstName.equals(obj.firstName) && this.lastName.equals(obj.lastName);
    }

}
