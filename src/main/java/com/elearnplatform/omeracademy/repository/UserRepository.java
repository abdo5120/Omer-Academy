package com.elearnplatform.omeracademy.repository;

import com.elearnplatform.omeracademy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    public User findByPhone(String phone);
}
