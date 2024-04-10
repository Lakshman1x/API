package com.example.mongo_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserInput {

    @NotBlank(message = "firstName can not be blank")
    @Size(min = 3, max = 25, message = "firstName size must be between 3 and 25")
    private String firstName;

    @NotBlank(message = "lastName cannot be blank")
    @Size(min = 3, max = 25, message = "lastName size must be between 3 and 25")
    private String lastName;

    public UserInput (String firstName, String lastName){
        this.firstName=firstName;
        this.lastName=lastName;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public  String getLastName(){return  this.lastName;}

}
