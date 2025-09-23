package com.elearnplatform.omeracademy.dto.course;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseRequestDto
{
    @NotBlank(message = "Course Title is Required")
    private String title;

    private String description;

    @NotBlank(message = "Grade Level ID is Required")
    private Integer gradeLevelId;

}
