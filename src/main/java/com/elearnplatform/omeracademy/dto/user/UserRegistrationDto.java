package com.elearnplatform.omeracademy.dto.user;

import com.elearnplatform.omeracademy.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UserRegistrationDto
{
    @Size(min = 5, max = 255 , message = "Name must be between 5 and 255")
    @NotBlank(message = "The Name is Required")
    private String fullName;

    @NotBlank(message = "birthday is Required")
    private LocalDate birthday;

    //@NotBlank(message = "The Email is Required")
    //@Email(message = "Email Format is not correct")
    //private String email;

    @NotBlank(message = "UserName is not correct")
    private String username;

    @NotBlank(message = "Phone Number is required")
    private String phone;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "New Password must be between 6 and 100")
    private String password;

    private Role role = Role.STUDENT; // Default role

    @NotNull(message = "Grade level ID is required")
    private long gradeLevelId;
}
