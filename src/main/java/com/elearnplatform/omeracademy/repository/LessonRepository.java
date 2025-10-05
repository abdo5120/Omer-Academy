package com.elearnplatform.omeracademy.repository;

import com.elearnplatform.omeracademy.entity.Lesson;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long>
{
    Long countByCourseId(Long courseId);

    List<Lesson> findByCourseIdOrderByOrderLesson(long courseId);
}
