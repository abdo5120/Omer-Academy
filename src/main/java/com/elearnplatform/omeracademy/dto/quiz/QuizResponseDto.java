package com.elearnplatform.omeracademy.dto.quiz;

import com.elearnplatform.omeracademy.entity.Lesson;
import com.elearnplatform.omeracademy.entity.Question;
import com.elearnplatform.omeracademy.entity.Submission;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class QuizResponseDto
{
    private Integer id;
    private String title;
    private String lessonTitle;
    private Integer lessonId;
    private String courseTitle;
    private Integer courseId;
    private List<QuestionResponseDto> questions;
}
