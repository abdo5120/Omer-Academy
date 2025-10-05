package com.elearnplatform.omeracademy.mapper;

import com.elearnplatform.omeracademy.dto.course.LessonRequestDto;
import com.elearnplatform.omeracademy.dto.course.LessonResponseDto;
import com.elearnplatform.omeracademy.entity.Course;
import com.elearnplatform.omeracademy.entity.Lesson;
import com.elearnplatform.omeracademy.exception.ResourceNotFoundException;
import com.elearnplatform.omeracademy.repository.CourseRepository;
import com.elearnplatform.omeracademy.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LessonMapper
{
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;

    public Lesson toLessonEntity(LessonRequestDto lessonRequestDto)
    {
        if (lessonRequestDto == null) return null;

        Lesson lesson = new Lesson();
        lesson.setTitle(lessonRequestDto.getTitle());
        lesson.setUrl(lessonRequestDto.getUrl());
        Course course = courseRepository.findById(lessonRequestDto.getCourseId())
                        .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        lesson.setCourse(course);

        // Auto-set position if not provided
        if (lesson.getOrderLesson() == null) {
            Long count = lessonRepository.countByCourseId(lessonRequestDto.getCourseId());
            lesson.setOrderLesson((long) count.intValue() + 1);
        }
        return lesson;
    }

    public LessonResponseDto toLessonResponseDto(Lesson lesson) {
        if (lesson == null) return null;

        LessonResponseDto lessonResponseDto = new LessonResponseDto();
        lessonResponseDto.setId(lesson.getId());
        lessonResponseDto.setTitle(lesson.getTitle());
        lessonResponseDto.setUrl(lesson.getUrl());
        lessonResponseDto.setOrderLesson(lesson.getOrderLesson());

        if (lesson.getCourse() != null) {
            lessonResponseDto.setCourseId(lesson.getCourse().getId());
            lessonResponseDto.setCourseTitle(lesson.getCourse().getTitle());
        }

        // Quiz
        /*
        if(lesson.getQuiz() != null) {
            lessonResponseDto.setQuizResponseDtos(lesson.getQuiz().stream()
                    .map(quiz -> ));
        }
        */
        return lessonResponseDto;
    }

    public List<LessonResponseDto> toLessonResponseDtoList(List<Lesson> lessons)
    {
        return lessons.stream()
                .map(this::toLessonResponseDto)
                .collect(Collectors.toList());
    }
/*
    public LessonSummaryDto toLessonSummaryDto(Lesson lesson) {
        if (lesson == null) return null;

        LessonSummaryDto lessonSummaryDto = new LessonSummaryDto();
        lessonSummaryDto.setId(lesson.getId());
        lessonSummaryDto.setTitle(lesson.getTitle());
        lessonSummaryDto.setOrderLesson(lesson.getOrderLesson());
        lessonSummaryDto.setHasQuiz(lesson.getQuizzes() != null && !lesson.getQuizzes().isEmpty());

        return lessonSummaryDto;
    }

    public List<LessonSummaryDto> toSummaryDtoList(List<Lesson> lessons) {
        return lessons.stream()
                .map(this::toLessonSummaryDto)
                .collect(Collectors.toList());
    }

 */
}
