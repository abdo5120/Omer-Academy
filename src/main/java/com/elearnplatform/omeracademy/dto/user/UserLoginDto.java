package com.elearnplatform.omeracademy.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginDto
{
    //@Email
    //@NotBlank(message = "Email is Required")
    //private String email;

    //@NotBlank(message = "Phone number is Required")
    //private String phone;

    @NotBlank(message = "Username is Required")
    private String username;

    @NotBlank(message = "Password is Required")
    private String password;
}
