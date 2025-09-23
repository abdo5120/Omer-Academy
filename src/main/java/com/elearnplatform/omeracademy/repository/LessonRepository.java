package com.elearnplatform.omeracademy.repository;

import com.elearnplatform.omeracademy.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long>
{
}
