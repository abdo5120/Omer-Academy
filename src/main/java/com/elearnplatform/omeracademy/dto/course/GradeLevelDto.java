package com.elearnplatform.omeracademy.dto.course;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class GradeLevelDto
{
    private long id;

    @NotBlank(message = "Grade Level Name is Required")
    private String name;
}

