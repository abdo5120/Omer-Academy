package com.elearnplatform.omeracademy.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UserUpdateDto
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

    @NotNull(message = "Grade level ID is required")
    private Long gradeLevelId;
}
