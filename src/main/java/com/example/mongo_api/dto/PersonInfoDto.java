package com.example.mongo_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PersonInfoDto {

    @NotBlank(message = "firstName can not be blank")
    @Size(min = 3, max = 25, message = "firstName size must be between 3 and 25")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "firstName can contain only letters")
    private String firstName;

    @NotBlank(message = "lastName cannot be blank")
    @Size(min = 3, max = 25, message = "lastName size must be between 3 and 25")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "lastName can contain only letters")
    private String lastName;

    public PersonInfoDto(String firstName, String lastName){
        this.firstName=firstName;
        this.lastName=lastName;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public  String getLastName(){return  this.lastName;}

}
