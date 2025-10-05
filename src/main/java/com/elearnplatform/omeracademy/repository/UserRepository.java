package com.elearnplatform.omeracademy.repository;

import com.elearnplatform.omeracademy.entity.Role;
import com.elearnplatform.omeracademy.entity.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    List<User> findByRole(Role role);

    List<User> findByRoleAndGradeLevelId(Role role, long gradeLevel_id);

    boolean existsByPhone(@NotBlank(message = "UserName is not correct") String username);
}
