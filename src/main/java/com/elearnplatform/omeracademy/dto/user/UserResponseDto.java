package com.elearnplatform.omeracademy.dto.user;

import com.elearnplatform.omeracademy.entity.Role;
import com.elearnplatform.omeracademy.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserResponseDto
{
    private Long id;
    private String name;
    //private String email;
    private String phone;
    private Status status;
    private Role role;
    private String gradeLevelName;
    private LocalDateTime createdAt;
}
