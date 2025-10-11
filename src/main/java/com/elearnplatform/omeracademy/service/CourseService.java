package com.elearnplatform.omeracademy.service;

import com.elearnplatform.omeracademy.dto.course.CourseRequestDto;
import com.elearnplatform.omeracademy.dto.course.CourseResponseDto;
import com.elearnplatform.omeracademy.entity.Course;
import com.elearnplatform.omeracademy.entity.GradeLevel;
import com.elearnplatform.omeracademy.exception.ResourceNotFoundException;
import com.elearnplatform.omeracademy.mapper.CourseMapper;
import com.elearnplatform.omeracademy.repository.CourseRepository;
import com.elearnplatform.omeracademy.repository.GradeLevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService
{
    private final CourseRepository courseRepository;
    private final GradeLevelRepository gradeLevelRepository;
    private final CourseMapper courseMapper;


    @Transactional
    public CourseResponseDto createCourse(CourseRequestDto courseRequestDto)
    {
        Course course = courseMapper.toCourseEntity(courseRequestDto);
        course = courseRepository.save(course);
        return courseMapper.toCourseResponseDto(course);
    }

    public CourseResponseDto getCourseById(long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        return courseMapper.toCourseResponseDto(course);
    }

    @Transactional(readOnly = true)
    public CourseResponseDto getCourseWithLessons(long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        return courseMapper.toCourseResponseDtoWithLessons(course);
    }

    public List<CourseResponseDto> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courseMapper.toResponseDtoList(courses);
    }

    public List<CourseResponseDto> getCoursesByGradeLevel(long gradeLevelId) {
        List<Course> courses = courseRepository.findByGradeLevelIdOrderByTitle(gradeLevelId);
        return courseMapper.toResponseDtoList(courses);
    }

    /*
    public List<CourseResponseDto> searchCourses(String keyword) {
        List<Course> courses = courseRepository.findByTitleContainingIgnoreCase(keyword);
        return courseMapper.toResponseDtoList(courses);
    }
     */

    public void deleteCourse(long id) {
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course not found");
        }

        courseRepository.deleteById(id);
    }

    public CourseResponseDto updateCourse(long id, CourseRequestDto courseRequestDto) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        if (courseRequestDto.getTitle() != null) {
            course.setTitle(courseRequestDto.getTitle());
        }
        if (courseRequestDto.getDescription() != null) {
            course.setDescription(courseRequestDto.getDescription());
        }
        if(courseRequestDto.getGradeLevelId() != null) {
            GradeLevel gradeLevel = gradeLevelRepository.findById(courseRequestDto.getGradeLevelId())
                    .orElseThrow(() -> new ResourceNotFoundException("GradeLevel not found"));
            course.setGradeLevel(gradeLevel);
        }

        Course updatedCourse = courseRepository.save(course);

        return courseMapper.toCourseResponseDto(updatedCourse);
    }

    public long getTotalCoursesCount()
    {
        return courseRepository.count();
    }

    public long getLessonsCountByCourse(long courseId)
    {
        return courseRepository.countCoursesById(courseId);
    }
}
