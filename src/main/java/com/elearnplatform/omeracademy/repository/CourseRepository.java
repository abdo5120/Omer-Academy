package com.elearnplatform.omeracademy.repository;

import com.elearnplatform.omeracademy.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>
{
    List<Course> findByGradeLevelIdOrderByTitle(long gradeLevelId);
}
