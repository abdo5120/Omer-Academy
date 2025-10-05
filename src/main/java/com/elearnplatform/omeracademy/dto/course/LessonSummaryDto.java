package com.elearnplatform.omeracademy.dto.course;

import lombok.Data;

@Data
public class LessonSummaryDto
{
    private long id;
    private String title;
    private int orderLesson;
    private boolean hasQuiz;
}
