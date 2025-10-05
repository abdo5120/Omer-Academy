package com.elearnplatform.omeracademy.mapper;

import com.elearnplatform.omeracademy.dto.course.CourseRequestDto;
import com.elearnplatform.omeracademy.dto.course.CourseResponseDto;
import com.elearnplatform.omeracademy.dto.course.LessonResponseDto;
import com.elearnplatform.omeracademy.entity.Course;
import com.elearnplatform.omeracademy.entity.GradeLevel;
import com.elearnplatform.omeracademy.entity.Lesson;
import com.elearnplatform.omeracademy.repository.GradeLevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CourseMapper
{
    public final GradeLevelRepository gradeLevelRepository;
    private final LessonMapper lessonMapper;


    public Course toCourseEntity(CourseRequestDto courseRequestDto)
    {
        if (courseRequestDto == null) return null;

        GradeLevel gradeLevel = gradeLevelRepository.findById(courseRequestDto.getGradeLevelId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid grade level"));

        Course course = new Course();
        course.setTitle(courseRequestDto.getTitle());
        course.setGradeLevel(gradeLevel);
        course.setDescription(courseRequestDto.getDescription());
        return course;
    }

    public CourseResponseDto toCourseResponseDto(Course course)
    {
        if (course == null) return null;

        CourseResponseDto courseResponseDto = new CourseResponseDto();
        courseResponseDto.setId(course.getId());
        courseResponseDto.setTitle(course.getTitle());
        courseResponseDto.setDescription(course.getDescription());

        if (course.getGradeLevel() != null) {
            GradeLevel gradeLevel = gradeLevelRepository.findById(course.getGradeLevel().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid grade level"));
            courseResponseDto.setGradeLevelName(course.getGradeLevel().getGradeLevelName());
        }

        if (course.getLessons() != null) {
            Lesson lesson =new Lesson();
            courseResponseDto.setLessonsCount(course.getLessons().size());
        }

        return courseResponseDto;
    }

    public List<CourseResponseDto> toResponseDtoList(List<Course> courses) {
        return courses.stream()
                .map(this::toCourseResponseDto)
                .collect(Collectors.toList());
    }


    public CourseResponseDto toCourseResponseDtoWithLessons(Course course) {
        CourseResponseDto courseResponseDto = toCourseResponseDto(course);

        if (course.getLessons() != null && !course.getLessons().isEmpty()) {
            List<LessonResponseDto> lessons = course.getLessons().stream()
                    .map(lessonMapper::toLessonResponseDto)
                    .collect(Collectors.toList());
            courseResponseDto.setLessonResponseDtos(lessons);
        }

        return courseResponseDto;
    }

}
