package com.elearnplatform.omeracademy.repository;

import com.elearnplatform.omeracademy.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>
{
}
