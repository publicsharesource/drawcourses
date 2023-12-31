package com.drawproject.dev.model;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class Profile {

    @NotBlank(message="FullName must not be blank")
    @Size(min=3, message="FullName must be at least 3 characters long")
    private String fullName;

    @NotBlank(message="Mobile number must not be blank")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotBlank(message="Email must not be blank")
    @Email(message = "Please provide a valid email address" )
    private String email;

    private String avatar;

    private int skill;

}
