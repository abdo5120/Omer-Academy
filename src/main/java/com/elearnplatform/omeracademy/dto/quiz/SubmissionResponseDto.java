package com.elearnplatform.omeracademy.dto.quiz;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class SubmissionResponseDto
{
    private Integer quizId;
    private String quizTitle;
    private String lessonTitle;
    private String courseTitle;
    private Long studentId;
    private String studentName;
    private Integer score;
    private Double percentage;
    private LocalDateTime submittedAt;
    private List<AnswerResultDto> results;
}
