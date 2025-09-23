package com.elearnplatform.omeracademy.dto.quiz;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class QuestionResponseDto
{
    private String questionText;
    private List<String> options;
    private String correctAnswer;
}
