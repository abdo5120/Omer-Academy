package com.elearnplatform.omeracademy.dto.course;

import lombok.Data;

import java.util.List;

@Data
public class CourseResponseDto
{
    private Long id;
    private String title;
    private String description;
    private String gradeLevelName;
    private Integer lessonsCount;
    private List<LessonResponseDto> lessonResponseDtos;
}
