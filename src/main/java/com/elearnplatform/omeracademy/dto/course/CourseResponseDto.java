package com.elearnplatform.omeracademy.dto.course;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CourseResponseDto
{
    private Integer id;
    private String title;
    private String description;
    private String gradeLevelName;
    private Integer lessonsCount;
    private List<LessonResponseDto> lessonResponseDtos;
}
