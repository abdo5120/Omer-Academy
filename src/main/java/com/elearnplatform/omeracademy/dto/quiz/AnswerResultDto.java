package com.elearnplatform.omeracademy.dto.quiz;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnswerResultDto
{
    private Long questionId;
    private String questionText;
    private String selectedAnswer;
    private String correctAnswer;
    private boolean isCorrect;
}
