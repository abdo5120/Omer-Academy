package com.elearnplatform.omeracademy.repository;

import com.elearnplatform.omeracademy.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long>
{

}
