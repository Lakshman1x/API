package com.example.mongo_api.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Person")
@Data
public class PersonEntity {

    @Id

    private  final String email;
    private final String firstName;


    private final String lastName;

    public PersonEntity(String email, String firstName, String lastName) {
        this.email=email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }
    public String getEmail(){return this.email;}


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        PersonEntity other = (PersonEntity) obj;
        return this.firstName.equals(other.firstName) && this.lastName.equals(other.lastName);
    }

    @Override
    public int hashCode() {
        int result = email.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        return result;
    }

}
