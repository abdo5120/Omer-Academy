package com.elearnplatform.omeracademy.dto.course;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LessonRequestDto
{
    @NotBlank(message = "Lesson Title is Required")
    private String title;

    private String url;

    @NotBlank(message = "Order Lesson is Required")
    private int orderLesson;

    @NotBlank(message = "Course ID is Required")
    private Integer courseId;
}
