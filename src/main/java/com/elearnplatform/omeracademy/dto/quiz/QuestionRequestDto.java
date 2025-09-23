package com.elearnplatform.omeracademy.dto.quiz;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class QuestionRequestDto
{
    @NotBlank(message = "Question Text is Required")
    private String questionText;

    @NotBlank(message = "Quiz ID is Required")
    private Integer quizId;

    @NotNull(message = "Option is Required")
    private List<String> options;

    @NotBlank(message = "Correct Answer is Required")
    private String correctAnswer;
}
