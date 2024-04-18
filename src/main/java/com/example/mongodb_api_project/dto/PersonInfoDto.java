package com.example.mongodb_api_project.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PersonInfoDto {

    @NotBlank(message = "Email cant be blank")
    @Size(min = 5, max = 100, message = "email size must be between 3 and 100")
    @Email(message = "Invalid email pattern")
    private String email;

    @NotBlank(message = "firstName can not be blank")
    @Size(min = 3, max = 25, message = "firstName size must be between 3 and 25")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "firstName can contain only letters")
    private String firstName;

    @NotBlank(message = "lastName cannot be blank")
    @Size(min = 3, max = 25, message = "lastName size must be between 3 and 25")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "lastName can contain only letters")
    private String lastName;

    public PersonInfoDto(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }
}