package com.elearnplatform.omeracademy.dto.user;

import com.elearnplatform.omeracademy.entity.Role;
import com.elearnplatform.omeracademy.entity.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegistrationDto
{
    @NotBlank(message = "The Name is Required")
    private String fullName;

    //@NotBlank(message = "The Email is Required")
    //@Email(message = "Email Format is not correct")
    //private String email;

    @NotBlank(message = "Phone Number is required")
    private String phone;

    @NotBlank(message = "Password is required")
    private String password;

    private Status status = Status.ACTIVE; // Default status

    private Role role = Role.STUDENT; // Default role

    @NotNull(message = "Grade level ID is required")
    private int gradeLevelId;
}
