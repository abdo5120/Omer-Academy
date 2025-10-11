package com.elearnplatform.omeracademy.service;

import com.elearnplatform.omeracademy.dto.course.LessonRequestDto;
import com.elearnplatform.omeracademy.dto.course.LessonResponseDto;
import com.elearnplatform.omeracademy.entity.Course;
import com.elearnplatform.omeracademy.entity.Lesson;
import com.elearnplatform.omeracademy.exception.ResourceNotFoundException;
import com.elearnplatform.omeracademy.mapper.LessonMapper;
import com.elearnplatform.omeracademy.repository.CourseRepository;
import com.elearnplatform.omeracademy.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService
{
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final LessonMapper lessonMapper;

    @Transactional
    public LessonResponseDto createLesson(LessonRequestDto lessonRequestDto)
    {
        Lesson lesson = lessonMapper.toLessonEntity(lessonRequestDto);
        lesson = lessonRepository.save(lesson);
        return lessonMapper.toLessonResponseDto(lesson);
    }

    public LessonResponseDto getLessonById(long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson is not found"));

        return lessonMapper.toLessonResponseDto(lesson);
    }

    public List<LessonResponseDto> getLessonsByCourse(Integer courseId) {
        List<Lesson> lessons = lessonRepository.findByCourseIdOrderByOrderLesson(courseId);
        return lessonMapper.toLessonResponseDtoList(lessons);
    }

    public void deleteLesson(long id) {
        if (!lessonRepository.existsById(id)) {
            throw new ResourceNotFoundException("Lesson is not found");
        }

        lessonRepository.deleteById(id);
    }

    public LessonResponseDto updateLesson(long id, LessonRequestDto lessonRequestDto) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson is not found"));

        if (lessonRequestDto.getTitle() != null) {
            lesson.setTitle(lessonRequestDto.getTitle());
        }
        if (lessonRequestDto.getUrl() != null) {
            lesson.setUrl(lessonRequestDto.getUrl());
        }
        if (lessonRequestDto.getOrderLesson() != null) {
            lesson.setOrderLesson(lessonRequestDto.getOrderLesson());
        }

        if (lessonRequestDto.getCourseId() != null && !lessonRequestDto.getCourseId().equals(lesson.getCourse().getId())) {
            Course course = courseRepository.findById(lessonRequestDto.getCourseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Lesson is not found"));
            lesson.setCourse(course);
        }

        Lesson updatedLesson = lessonRepository.save(lesson);

        return lessonMapper.toLessonResponseDto(updatedLesson);
    }

    public long getTotalLessonsCount()
    {
        return lessonRepository.count();
    }


}
