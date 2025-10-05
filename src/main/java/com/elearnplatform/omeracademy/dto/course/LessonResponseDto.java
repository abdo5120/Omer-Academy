package com.elearnplatform.omeracademy.dto.course;

import com.elearnplatform.omeracademy.dto.quiz.QuizResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class LessonResponseDto
{
    private Long id;
    private String title;
    private String url;
    private long orderLesson;
    private String courseTitle;
    private Long courseId;
    private List<QuizResponseDto> quizResponseDtos;
}
