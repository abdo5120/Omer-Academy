package com.elearnplatform.omeracademy.controller;

import com.elearnplatform.omeracademy.dto.course.LessonRequestDto;
import com.elearnplatform.omeracademy.dto.course.LessonResponseDto;
import com.elearnplatform.omeracademy.response.ApiResponse;
import com.elearnplatform.omeracademy.service.CourseService;
import com.elearnplatform.omeracademy.service.LessonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class LessonController
{
    private final LessonService lessonService;
    private final CourseService courseService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<LessonResponseDto>> createLesson(
            @Valid @RequestBody LessonRequestDto lessonDto) {

        LessonResponseDto lesson = lessonService.createLesson(lessonDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("The lesson was created successfully.", lesson));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LessonResponseDto>> getLessonById(
            @PathVariable Integer id) {

        LessonResponseDto lesson = lessonService.getLessonById(id);
        return ResponseEntity.ok(ApiResponse.success(lesson));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<ApiResponse<List<LessonResponseDto>>> getLessonsByCourse(
            @PathVariable Integer courseId) {

        List<LessonResponseDto> lessons = lessonService.getLessonsByCourse(courseId);
        return ResponseEntity.ok(ApiResponse.success(lessons));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<LessonResponseDto>> updateLesson(
            @PathVariable Integer id,
            @Valid @RequestBody LessonRequestDto lessonDto) {

        LessonResponseDto lesson = lessonService.updateLesson(id, lessonDto);
        return ResponseEntity.ok(ApiResponse.success("The lesson has been updated successfully.", lesson));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<Object>> deleteLesson(@PathVariable Integer id) {
        lessonService.deleteLesson(id);
        return ResponseEntity.ok(ApiResponse.success("The lesson was successfully deleted."));
    }

    @DeleteMapping("/course/{courseId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Object>> deleteAllLessonsInCourse(
            @PathVariable Integer courseId) {

        List<LessonResponseDto> lessons = lessonService.getLessonsByCourse(courseId);

        for (LessonResponseDto lesson : lessons) {
            lessonService.deleteLesson(lesson.getId());
        }

        return ResponseEntity.ok(
                ApiResponse.success("All course lessons have been successfully deleted --> ( " + lessons.size() + " ) Lessons")
        );
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse<Long>> getTotalLessonsCount() {
        long count = lessonService.getTotalLessonsCount();
        return ResponseEntity.ok(ApiResponse.success(count));
    }

    @GetMapping("/course/{courseId}/count")
    public ResponseEntity<ApiResponse<Long>> getLessonsCountByCourse(
            @PathVariable Integer courseId) {

        long count = courseService.getLessonsCountByCourse(courseId);
        return ResponseEntity.ok(ApiResponse.success(count));
    }

}
