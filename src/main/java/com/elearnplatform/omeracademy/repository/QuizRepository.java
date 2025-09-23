package com.elearnplatform.omeracademy.repository;

import com.elearnplatform.omeracademy.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long>
{
}
