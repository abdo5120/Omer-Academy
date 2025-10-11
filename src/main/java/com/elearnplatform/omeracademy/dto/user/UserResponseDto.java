package com.elearnplatform.omeracademy.dto.user;

import com.elearnplatform.omeracademy.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto
{
    private Long id;
    private String fullName;
    private LocalDate birthday;
    //private String email;
    private String username;
    private String phone;
    private String role;
    private String gradeLevelName;
    private LocalDateTime createdAt;
}
