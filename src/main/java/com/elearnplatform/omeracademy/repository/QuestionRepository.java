package com.elearnplatform.omeracademy.repository;

import com.elearnplatform.omeracademy.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>
{
}
