package com.elearnplatform.omeracademy.controller;

import com.elearnplatform.omeracademy.dto.course.CourseResponseDto;
import com.elearnplatform.omeracademy.dto.course.GradeLevelDto;
import com.elearnplatform.omeracademy.dto.user.UserResponseDto;
import com.elearnplatform.omeracademy.response.ApiResponse;
import com.elearnplatform.omeracademy.service.CourseService;
import com.elearnplatform.omeracademy.service.GradeLevelService;
import com.elearnplatform.omeracademy.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/grade-levels")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class GradeLevelController
{
    private final GradeLevelService gradeLevelService;
    private final CourseService courseService;
    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<GradeLevelDto>> createGradeLevel(
            @Valid @RequestBody GradeLevelDto gradeLevelDto) {

        GradeLevelDto gradeLevel = gradeLevelService.createGradeLevel(gradeLevelDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("The GradeLevel has been successfully created.", gradeLevel));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GradeLevelDto>>> getAllGradeLevels() {
        List<GradeLevelDto> gradeLevels = gradeLevelService.getAllGradeLevels();
        return ResponseEntity.ok(ApiResponse.success(gradeLevels));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GradeLevelDto>> getGradeLevelById(
            @PathVariable Long id) {

        GradeLevelDto gradeLevel = gradeLevelService.getGradeLevelById(id);
        return ResponseEntity.ok(ApiResponse.success(gradeLevel));
    }

    @GetMapping("/{id}/students")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> getStudentsByGradeLevel(
            @PathVariable Long id) {

        List<UserResponseDto> students = userService.getStudentsByGradeLevel(id);
        return ResponseEntity.ok(ApiResponse.success(students));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<GradeLevelDto>> updateGradeLevel(
            @PathVariable Long id,
            @Valid @RequestBody GradeLevelDto gradeLevelDto) {

        // Delete the old
        gradeLevelService.deleteGradeLevel(id);

        // إنشاء بالبيانات الجديدة مع نفس الـ ID
        gradeLevelDto.setId(id);
        GradeLevelDto updated = gradeLevelService.createGradeLevel(gradeLevelDto);

        return ResponseEntity.ok(
                ApiResponse.success("The Grade Level has been successfully updated.", updated)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Object>> deleteGradeLevel(@PathVariable Long id) {
        GradeLevelDto gradeLevel = gradeLevelService.getGradeLevelById(id);
        gradeLevelService.deleteGradeLevel(id);
        return ResponseEntity.ok(ApiResponse.success("The Grade Level has been successfully deleted : " + gradeLevel.getName()));
    }

    @GetMapping("/{id}/students/count")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<Integer>> getStudentsCountByGradeLevel(
            @PathVariable Long id) {

        List<UserResponseDto> students = userService.getStudentsByGradeLevel(id);
        return ResponseEntity.ok(ApiResponse.success(students.size()));
    }

    @GetMapping("/{id}/summary")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getGradeLevelSummary(
            @PathVariable Long id) {

        GradeLevelDto gradeLevel = gradeLevelService.getGradeLevelById(id);
        List<CourseResponseDto> courses = courseService.getCoursesByGradeLevel(id);
        List<UserResponseDto> students = userService.getStudentsByGradeLevel(id);

        long totalLessons = courses.stream()
                .mapToLong(course -> courseService.getLessonsCountByCourse(course.getId()))
                .sum();

        Map<String, Object> summary = new HashMap<>();
        summary.put("gradeLevel", gradeLevel);
        summary.put("coursesCount", courses.size());
        summary.put("studentsCount", students.size());
        summary.put("totalLessons", totalLessons);
        summary.put("courses", courses);

        return ResponseEntity.ok(ApiResponse.success(summary));
    }
}
