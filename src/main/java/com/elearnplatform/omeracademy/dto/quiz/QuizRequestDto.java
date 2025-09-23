package com.elearnplatform.omeracademy.dto.quiz;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class QuizRequestDto
{
    @NotBlank(message = "Quiz Title is Required")
    private String title;

    @NotBlank(message = "Lesson Id is Required")
    private long lessonId;
}
