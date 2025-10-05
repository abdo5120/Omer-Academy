package com.elearnplatform.omeracademy.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordChangeDto
{
    @NotBlank(message = "Current Password is Required")
    private String currentPassword;

    @NotBlank(message = "New Password is Required")
    @Size(min = 6, max = 100, message = "New Password must be between 6 and 100")
    private String newPassword;

    @NotBlank(message = "Confirm Password is Required")
    private String confirmPassword;
}
