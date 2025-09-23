package com.elearnplatform.omeracademy.service;

import com.elearnplatform.omeracademy.entity.User;
import com.elearnplatform.omeracademy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;

    public List<User> getAllUser()
    {
        return userRepository.findAll();
    }

}
