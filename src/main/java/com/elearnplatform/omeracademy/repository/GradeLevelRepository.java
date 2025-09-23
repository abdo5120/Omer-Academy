package com.elearnplatform.omeracademy.repository;

import com.elearnplatform.omeracademy.entity.GradeLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeLevelRepository extends JpaRepository<GradeLevel, Long>
{
}
