package com.example.LinkMongo.Model;

import lombok.NoArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Person")
@NoArgsConstructor
@Data
public class Person {

    @Id
    private String firstName;

    private String lastName;

    public Person (String firstName, String lastName){
        this.firstName=firstName;
        this.lastName=lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public boolean equals(Person obj) {
        return this.firstName.equals(obj.firstName) && this.lastName.equals(obj.lastName);
    }
}
