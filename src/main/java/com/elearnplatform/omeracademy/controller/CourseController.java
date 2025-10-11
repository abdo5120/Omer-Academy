package com.elearnplatform.omeracademy.controller;

import com.elearnplatform.omeracademy.dto.course.CourseRequestDto;
import com.elearnplatform.omeracademy.dto.course.CourseResponseDto;
import com.elearnplatform.omeracademy.response.ApiResponse;
import com.elearnplatform.omeracademy.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseController
{
    private final CourseService courseService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<CourseResponseDto>> createCourse(
            @Valid @RequestBody CourseRequestDto courseDto) {

        CourseResponseDto course = courseService.createCourse(courseDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("The course was created successfully.", course));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CourseResponseDto>>> getAllCourses() {
        List<CourseResponseDto> courses = courseService.getAllCourses();
        return ResponseEntity.ok(ApiResponse.success(courses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponseDto>> getCourseById(
            @PathVariable Integer id) {

        CourseResponseDto course = courseService.getCourseById(id);
        return ResponseEntity.ok(ApiResponse.success(course));
    }

    @GetMapping("/{id}/with-lessons")
    public ResponseEntity<ApiResponse<CourseResponseDto>> getCourseWithLessons(
            @PathVariable Integer id) {

        CourseResponseDto course = courseService.getCourseWithLessons(id);
        return ResponseEntity.ok(ApiResponse.success(course));
    }

    @GetMapping("/grade-level/{gradeLevelId}")
    public ResponseEntity<ApiResponse<List<CourseResponseDto>>> getCoursesByGradeLevel(
            @PathVariable Integer gradeLevelId) {

        List<CourseResponseDto> courses = courseService.getCoursesByGradeLevel(gradeLevelId);
        return ResponseEntity.ok(ApiResponse.success(courses));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<CourseResponseDto>> updateCourse(
            @PathVariable Integer id,
            @Valid @RequestBody CourseRequestDto courseDto) {

        CourseResponseDto course = courseService.updateCourse(id, courseDto);
        return ResponseEntity.ok(ApiResponse.success("The course has been updated successfully.", course));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Object>> deleteCourse(@PathVariable Integer id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok(ApiResponse.success("The course has been successfully deleted."));
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse<Long>> getCoursesCount() {
        long count = courseService.getTotalCoursesCount();
        return ResponseEntity.ok(ApiResponse.success(count));
    }

    @GetMapping("/{courseId}/lessons/count")
    public ResponseEntity<ApiResponse<Long>> getLessonsCountByCourse(
            @PathVariable Integer courseId) {

        long count = courseService.getLessonsCountByCourse(courseId);
        return ResponseEntity.ok(ApiResponse.success(count));
    }

}
