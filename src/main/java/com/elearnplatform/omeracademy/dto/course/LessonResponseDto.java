package com.elearnplatform.omeracademy.dto.course;

import com.elearnplatform.omeracademy.dto.quiz.QuizResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LessonResponseDto
{
    private Integer id;
    private String title;
    private String videoUrl;
    private Integer position;
    private String courseTitle;
    private Integer courseId;
    private List<QuizResponseDto> quizResponseDtos;
}
