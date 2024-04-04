package com.example.LinkMongo.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Person")
@NoArgsConstructor
@Data
public class Person {

    @Id
    @NotNull(message = "First name cant be null")
    @NotBlank(message = "First name not be blank")
    @Size(min = 3, max = 25)
    private String firstName;

    @NotNull(message = "Last name cant be null")
    @NotBlank(message = "Last not be blank")
    @Size(min = 3, max = 25)
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
